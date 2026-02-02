package com.example.eventpassfront

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eventpassfront.ui.navigation.Screen
import com.example.eventpassfront.ui.screens.eventDetail.EventDetailScreen
import com.example.eventpassfront.ui.screens.eventRegister.RegisterScreen
import com.example.eventpassfront.ui.screens.eventsList.EventsListScreen
import com.example.eventpassfront.ui.screens.home.HomeScreen
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

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    topBar = {
                        TopAppBar(
                            title = {
                                val currentTitle = when {
                                    currentRoute == "main_pager" -> {
                                        if (pagerState.currentPage == 0) "EventPass" else "Explorar"
                                    }
                                    currentRoute?.startsWith("register") == true -> "Registro"
                                    currentRoute?.startsWith("detalle_evento") == true -> "Detalles"
                                    else -> "EventPass"
                                }
                                Text(
                                    text = currentTitle,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            navigationIcon = {
                                if (currentRoute?.startsWith("register") == true || currentRoute?.startsWith("detalle_evento") == true) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                titleContentColor = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    },
                    bottomBar = {
                        if (currentRoute == "main_pager") {
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
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "main_pager",
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    ) {
                        composable("main_pager") {
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier.fillMaxSize()
                            ) { page ->
                                when (page) {
                                    0 -> HomeScreen(modifier = Modifier.fillMaxSize(), pagerState = pagerState, scope = scope, navController = navController)
                                    1 -> EventsListScreen(modifier = Modifier.fillMaxSize(), navController = navController)
                                }
                            }
                        }

                        composable(
                            route = Screen.Register.route,
                            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val eventId = backStackEntry.arguments?.getInt("eventId") ?: 0
                            RegisterScreen(modifier = Modifier.fillMaxSize(), eventId = eventId)
                        }

                        composable(
                            route = Screen.Details.route,
                            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val eventId = backStackEntry.arguments?.getInt("eventId") ?: 0
                            EventDetailScreen(modifier = Modifier.fillMaxSize(), eventId = eventId, navController = navController)
                        }
                    }
                }
            }
        }
    }
}