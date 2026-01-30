package com.example.eventpassfront.modelos

import com.google.gson.annotations.SerializedName

data class Evento(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val imagenRes: String,
    @SerializedName("categoriaNombre") val categoria: String,
    @SerializedName("ubicacionNombre") val ubicacion: String
)