package com.example.eventpassfront.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eventpassfront.ui.components.eventCards.Card1

@Composable
fun HomeScreen(modifier: Modifier) {
    Box(modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Text(
                    text = "Evento más próximo",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            item {
                Card1(
                    title = "Neon Nights Festival",
                    location = "Downtown Arena • 8:00 PM",
                    date = "Jueves, 24 de Octubre",
                    imageUrl = "",
                )
            }

            item {
                Text(
                    text = "Los 5 eventos más populares",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            item {
                Card1(
                    title = "Neon Nights Festival",
                    location = "Downtown Arena • 8:00 PM",
                    date = "Jueves, 24 de Octubre",
                    imageUrl = "",
                )
            }
        }
    }
}