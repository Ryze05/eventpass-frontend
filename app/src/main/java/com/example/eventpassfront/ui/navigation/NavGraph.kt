package com.example.eventpassfront.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    sealed class BottomBarScreen(
        route: String,
        val title: String,
        val icon: ImageVector
    ) : Screen(route) {
        data object Home : BottomBarScreen("home", "Inicio", Icons.Default.Home)
        data object EventsList : BottomBarScreen("events_list", "Eventos", Icons.Default.Place)
    }

    data object Register : Screen("register/{eventId}")
}