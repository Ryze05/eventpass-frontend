package com.example.eventpassfront.ui.screens.eventsList

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
        viewModelScope.launch {
            fetchEventosLogic(null)
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun refreshEvents() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            fetchEventosLogic(state.value.selectedCategoryId)
            _state.update { it.copy(isRefreshing = false) }
        }
    }

    fun fetchEventos(categoriaId: Int? = null) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            fetchEventosLogic(categoriaId)
            _state.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun fetchEventosLogic(categoriaId: Int? = null) {
        _state.update { it.copy(selectedCategoryId = categoriaId) }
        try {
            val url = if (categoriaId != null) "${KtorClient.BASE_URL}/eventos?categoria=$categoriaId"
            else "${KtorClient.BASE_URL}/eventos"

            val response = KtorClient.httpClient.get(url)
            if (response.status == HttpStatusCode.OK) {
                val eventos = response.body<List<Evento>>()
                _state.update { it.copy(events = eventos) }
            } else {
                _state.update { it.copy(events = emptyList()) }
            }
        } catch (e: Exception) {
            _state.update { it.copy(errorMessage = e.message) }
        }
    }

    fun fetchCategorias() {
        viewModelScope.launch {
            try {
                val response = KtorClient.httpClient.get("${KtorClient.BASE_URL}/categorias")
                if (response.status == HttpStatusCode.OK) {
                    val categorias = response.body<List<Categoria>>()
                    _state.update { it.copy(categories = categorias) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message) }
            }
        }
    }
}