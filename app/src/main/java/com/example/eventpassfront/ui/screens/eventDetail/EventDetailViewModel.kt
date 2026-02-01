package com.example.eventpassfront.ui.screens.eventDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventpassfront.modelos.Evento
import com.example.eventpassfront.network.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventDetailViewModel: ViewModel() {
    private val _state = MutableStateFlow(EventDetailState())
    val state = _state.asStateFlow()

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