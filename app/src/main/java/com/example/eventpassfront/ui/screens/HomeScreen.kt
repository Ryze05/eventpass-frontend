package com.example.eventpassfront.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eventpassfront.ui.components.eventCards.Card1

@Composable
fun HomeScreen(modifier: Modifier) {
    Box(modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Card1(
                title = "Neon Nights Festival",
                location = "Downtown Arena • 8:00 PM",
                date = "Jueves, 24 de Octubre",
                imageUrl = "",
            )
        }
    }
}