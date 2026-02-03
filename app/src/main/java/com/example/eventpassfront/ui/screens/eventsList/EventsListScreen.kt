package com.example.eventpassfront.ui.screens.eventsList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eventpassfront.ui.components.chips.CategoryChip
import com.example.eventpassfront.ui.components.eventCards.GridCard
import com.example.eventpassfront.ui.theme.DeepOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsListScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: EventsListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val isInitialLoading = state.isLoading && state.categories.isEmpty() && state.events.isEmpty() && state.selectedCategoryId == null

    PullToRefreshBox(
        isRefreshing = state.isLoading && !isInitialLoading,
        onRefresh = { viewModel.fetchEventos(state.selectedCategoryId) },
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
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    item {
                        CategoryChip(
                            text = "Todos",
                            isSelected = state.selectedCategoryId == null,
                            onSelected = { viewModel.fetchEventos(null) }
                        )
                    }

                    items(state.categories) {
                        CategoryChip(
                            text = it.nombre,
                            isSelected = state.selectedCategoryId == it.id,
                            onSelected = { viewModel.fetchEventos(it.id) }
                        )
                    }
                }

                if (state.events.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay eventos en esta categoría",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(30.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.events) {
                            GridCard(
                                evento = it,
                                onEventClick = {
                                    navController.navigate("detalle_evento/${it.id}")
                                }
                            )
                        }
                    }
                    /*LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.events) {
                            GridCard(
                                evento = it,
                                onEventClick = {
                                    navController.navigate("detalle_evento/${it.id}")
                                }
                            )
                        }
                    }*/
                }
            }
        }
    }
}