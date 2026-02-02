package com.example.eventpassfront.ui.screens.eventRegister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventpassfront.modelos.Asistente
import com.example.eventpassfront.modelos.Evento
import com.example.eventpassfront.network.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventRegisterViewModel: ViewModel() {
    private val _state = MutableStateFlow(EventRegisterState())
    val state = _state.asStateFlow()

    fun updateName(nuevoNombre: String) {
        _state.update { it.copy(nombre = nuevoNombre) }
    }

    fun updateEmail(nuevoEmail: String) {
        _state.update { it.copy(email = nuevoEmail) }
    }

    fun updateTelefono(nuevoTelefono: String) {
        _state.update { it.copy(telefono = nuevoTelefono) }
    }

    fun registerAssistant(idEvento: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val asistente = Asistente(
                    nombre = _state.value.nombre,
                    email = _state.value.email,
                    telefono = _state.value.telefono,
                    eventoId = idEvento
                )

                val response = KtorClient.httpClient.post("${KtorClient.BASE_URL}/asistentes") {
                    contentType(ContentType.Application.Json)
                    setBody(asistente)
                }

                if (response.status == HttpStatusCode.Created || response.status == HttpStatusCode.OK) {
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                } else {
                    _state.update { it.copy(isLoading = false, errorMessage = "Error: ${response.status}") }
                }

            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    /*fun registerAssistant(idEvento: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val payload = mapOf(
                    "nombre" to _state.value.nombre,
                    "email" to _state.value.email,
                    "telefono" to _state.value.telefono,
                    "eventoId" to idEvento
                )

                val response = KtorClient.httpClient.post("${KtorClient.BASE_URL}/asistentes") {
                    contentType(ContentType.Application.Json)
                    setBody(payload)
                }

                if (response.status == HttpStatusCode.Created || response.status == HttpStatusCode.OK) {
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                } else {
                    _state.update { it.copy(isLoading = false, errorMessage = "Error: ${response.status}") }
                }

            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }*/

    fun loadEvent(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val response = KtorClient.httpClient.get("${KtorClient.BASE_URL}/eventos/$id")

                if (response.status == HttpStatusCode.OK) {
                    val evento = response.body<Evento>()
                    _state.update { it.copy(isLoading = false, evento = evento) }
                } else if (response.status == HttpStatusCode.NotFound) {
                    _state.update { it.copy(isLoading = false, errorMessage = "Evento no encontrado") }
                } else {
                    _state.update { it.copy(isLoading = false, errorMessage = "Error del servidor: ${response.status}") }
                }

            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }
}