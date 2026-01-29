package com.example.eventpassfront

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventpassfront.ui.navigation.Screen
import com.example.eventpassfront.ui.screens.EventsListScreen
import com.example.eventpassfront.ui.screens.HomeScreen
import com.example.eventpassfront.ui.theme.EventPassFrontTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventPassFrontTheme {
                val navController = rememberNavController()
                val tabs = listOf(Screen.BottomBarScreen.Home, Screen.BottomBarScreen.EventsList)
                val pagerState = rememberPagerState(pageCount = { tabs.size })
                val scope = rememberCoroutineScope()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    topBar = {
                        TopAppBar(
                            title = {
                                val currentTitle = when (pagerState.currentPage) {
                                    0 -> "EventPass"
                                    1 -> "Explorar Eventos"
                                    else -> "Registro"
                                }
                                Text(
                                    text = currentTitle,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                titleContentColor = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    },
                    bottomBar = {
                        NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                            tabs.forEachIndexed { index, screen ->
                                val isSelected = pagerState.currentPage == index
                                NavigationBarItem(
                                    selected = isSelected,
                                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                                    icon = {
                                        Icon(
                                            screen.icon,
                                            contentDescription = screen.title,
                                            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    },
                                    label = {
                                        Text(
                                            screen.title,
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colorScheme.surface)
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "main_pager",
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable("main_pager") {
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier.fillMaxSize()
                            ) { page ->
                                when (page) {
                                    0 -> HomeScreen(modifier = Modifier.padding(innerPadding))
                                    1 -> EventsListScreen(modifier = Modifier.padding(innerPadding))
                                }
                            }
                        }

                        //TODO registro
                    }
                }
            }
        }
    }
}