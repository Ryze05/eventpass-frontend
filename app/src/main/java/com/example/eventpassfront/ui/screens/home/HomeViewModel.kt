package com.example.eventpassfront.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventpassfront.modelos.Evento
import com.example.eventpassfront.network.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        fetchHomeData()
    }

    fun fetchHomeData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val nextEvent: List<Evento> = KtorClient.httpClient
                    .get("${KtorClient.BASE_URL}/eventos/proximos?limit=1")
                    .body()

                val popularEvents: List<Evento> = KtorClient.httpClient
                    .get("${KtorClient.BASE_URL}/eventos/populares")
                    .body()

                _state.update { it.copy(isLoading = false, nextEvent = nextEvent.firstOrNull(), popularEvents = popularEvents) }

            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }
}