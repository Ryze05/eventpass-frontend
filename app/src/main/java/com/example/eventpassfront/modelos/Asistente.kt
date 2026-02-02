package com.example.eventpassfront.modelos

import kotlinx.serialization.Serializable

@Serializable
data class Asistente(
    val nombre: String,
    val email: String,
    val telefono: String,
    val eventoId: Int
)