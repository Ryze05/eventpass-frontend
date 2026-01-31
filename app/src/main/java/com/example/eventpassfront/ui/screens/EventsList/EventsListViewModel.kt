package com.example.eventpassfront.ui.screens.EventsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventpassfront.modelos.Categoria
import com.example.eventpassfront.modelos.Evento
import com.example.eventpassfront.network.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventsListViewModel: ViewModel() {
    private val _state = MutableStateFlow(EventsListState())
    val state = _state.asStateFlow()

    init {
        fetchCategorias()
        fetchEventos()
    }

    fun fetchCategorias() {
        viewModelScope.launch {
            try {
                val response = KtorClient.httpClient
                    .get("${KtorClient.BASE_URL}/categorias")

                if (response.status == HttpStatusCode.OK) {
                    val categorias = response.body<List<Categoria>>()
                    _state.update { it.copy(categories = categorias) }
                } else if (response.status == HttpStatusCode.NoContent) {
                    _state.update { it.copy(categories = emptyList()) }
                }

            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    fun fetchEventos(categoriaId: Int? = null) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, selectedCategoryId = categoriaId, errorMessage = null) }

            try {

                val url = if (categoriaId != null) {
                    "${KtorClient.BASE_URL}/eventos?categoria=$categoriaId"
                } else {
                    "${KtorClient.BASE_URL}/eventos"
                }

                val response = KtorClient.httpClient.get(url)

                if (response.status == HttpStatusCode.OK) {
                    val eventos = response.body<List<Evento>>()
                    _state.update { it.copy(isLoading = false, events = eventos) }
                } else if (response.status == HttpStatusCode.NoContent) {
                    _state.update { it.copy(isLoading = false, events = emptyList()) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }
}