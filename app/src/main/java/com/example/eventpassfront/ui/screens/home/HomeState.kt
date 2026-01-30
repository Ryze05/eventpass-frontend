package com.example.eventpassfront.ui.screens.home

import com.example.eventpassfront.modelos.Evento

data class HomeState(
    val isLoading: Boolean = false,
    val nextEvent: Evento? = null,
    val popularEvents: List<Evento> = emptyList(),
    val errorMessage: String? = null
)