package com.example.eventpassfront.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eventpassfront.ui.components.eventCards.Card1
import com.example.eventpassfront.ui.components.eventCards.Card2
import com.example.eventpassfront.ui.theme.DeepOrange
import com.example.eventpassfront.ui.utils.getDrawableId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel = viewModel(),
    pagerState: PagerState,
    scope: CoroutineScope,
    navController: NavController
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val isInitialLoading =
        state.isLoading && state.nextEvent == null && state.popularEvents.isEmpty()

    PullToRefreshBox(
        isRefreshing = state.isLoading && !isInitialLoading,
        onRefresh = { viewModel.fetchHomeData() },
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        if (isInitialLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DeepOrange)
            }
        } else if (state.errorMessage != null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.errorMessage!!, color = Color.Red)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                state.nextEvent?.let {
                    item {
                        Text(
                            text = "Evento más próximo",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }

                    item {

                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = { value ->
                                when (value) {
                                    SwipeToDismissBoxValue.StartToEnd -> {
                                        navController.navigate("register/${it.id}")
                                        false
                                    }

                                    SwipeToDismissBoxValue.EndToStart -> {
                                        navController.navigate("detalle_evento/${it.id}")
                                        false
                                    }

                                    else -> false
                                }
                            }
                        )

                        SwipeToDismissBox(
                            state = dismissState,
                            backgroundContent = {
                                val backgroundColor = when (dismissState.targetValue) {
                                    SwipeToDismissBoxValue.StartToEnd -> Color(0xFF2E7D32).copy(alpha = 0.7f)
                                    SwipeToDismissBoxValue.EndToStart -> DeepOrange.copy(alpha = 0.7f)
                                    else -> Color.Transparent
                                }

                                val alignment = if (dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd)
                                    Alignment.CenterStart else Alignment.CenterEnd

                                val icon = if (dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd) {
                                    Icons.Default.Person
                                } else {
                                    Icons.Default.Info
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(backgroundColor, RoundedCornerShape(28.dp))
                                        .padding(horizontal = 24.dp),
                                    contentAlignment = alignment
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            },
                            content = {
                                Card1(
                                    title = it.titulo,
                                    location = it.ubicacion,
                                    date = it.fecha,
                                    imageRes = getDrawableId(it.imagenRes),
                                    onDetailClick = {
                                        navController.navigate("detalle_evento/${it.id}")
                                    }
                                )
                            }
                        )

                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Los 5 eventos más populares",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                        )

                        TextButton(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(1)
                                }
                            }
                        ) {
                            Text(
                                text = "Todos",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }

                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.popularEvents) {
                            Card2(
                                category = it.categoria,
                                title = it.titulo,
                                imageRes = getDrawableId(it.imagenRes),
                                onDetailClick = {
                                    navController.navigate("detalle_evento/${it.id}")
                                }
                            )
                        }
                    }
                }
            }

        }
    }
}