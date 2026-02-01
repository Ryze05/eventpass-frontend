package com.example.eventpassfront.ui.screens.eventDetail

import com.example.eventpassfront.modelos.Evento

data class EventDetailState(
    val isLoading: Boolean = false,
    val evento: Evento? = null,
    val errorMessage: String? = null
)