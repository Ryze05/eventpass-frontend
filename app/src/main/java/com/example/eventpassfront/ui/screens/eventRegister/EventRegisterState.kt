package com.example.eventpassfront.ui.screens.eventRegister

import com.example.eventpassfront.modelos.Evento

data class EventRegisterState(
    val nombre: String = "",
    val email: String = "",
    val telefono: String = "",
    val idEvento: Int = 0,
    val evento: Evento? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val nombreError: String? = null,
    val emailError: String? = null,
    val telefonoError: String? = null
)