package com.example.eventpassfront.ui.screens.EventsList

import com.example.eventpassfront.modelos.Categoria
import com.example.eventpassfront.modelos.Evento

data class EventsListState(
    val isLoading: Boolean = false,
    val categories: List<Categoria> = emptyList(),
    val events: List<Evento> = emptyList(),
    val selectedCategoryId: Int? = null,
    val errorMessage: String? = null
)