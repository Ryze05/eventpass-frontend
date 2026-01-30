package com.example.eventpassfront.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.eventpassfront.ui.components.eventCards.Card1
import com.example.eventpassfront.ui.components.eventCards.Card2
import com.example.eventpassfront.ui.theme.DeepOrange

@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel = HomeViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.isLoading) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = DeepOrange)
        }
    } else if (state.errorMessage != null) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = state.errorMessage!!, color = Color.Red)
        }
    } else {
        Box(modifier.fillMaxSize()) {
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
                        Card1(
                            title = it.titulo,
                            location = it.ubicacion,
                            date = it.fecha,
                            imageRes = getDrawableId(it.imagenRes),
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
                            onClick = {/**/}
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
                        //contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.popularEvents) {
                            Card2(
                                category = it.categoria,
                                title = it.titulo,
                                imageRes = getDrawableId(it.imagenRes)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun getDrawableId(imagenRes: String): Int {
    val context = androidx.compose.ui.platform.LocalContext.current

    val name = imagenRes.substringBefore(".")

    val id = context.resources.getIdentifier(name, "drawable", context.packageName)

    return if (id != 0) id else com.example.eventpassfront.R.drawable.concierto
}