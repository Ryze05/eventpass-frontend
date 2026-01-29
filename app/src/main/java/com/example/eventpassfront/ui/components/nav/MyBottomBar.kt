package com.example.eventpassfront.ui.components.nav

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.example.eventpassfront.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun MyBottomBar() {
    val tabs = listOf(Screen.BottomBarScreen.Home, Screen.BottomBarScreen.EventsList)
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        tabs.forEachIndexed { index, screen ->
            val isSelected = pagerState.currentPage == index
            NavigationBarItem(
                selected = isSelected,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                icon = { Icon(screen.icon, contentDescription = screen.title, tint = if(isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant) },
                label = { Text(screen.title, color = if(isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant) },
                colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colorScheme.surface)
            )
        }
    }
}