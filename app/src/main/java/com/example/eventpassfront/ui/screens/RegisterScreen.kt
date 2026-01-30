package com.example.eventpassfront.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RegisterScreen(
    modifier: Modifier,
    eventId: Int
) {
    Box(modifier.fillMaxSize()) {
        Text("Register screen, event ID: $eventId")
    }
}
