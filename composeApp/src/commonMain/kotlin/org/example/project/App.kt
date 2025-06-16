package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import org.example.project.navigation.HistoryScreenRoutes
import org.example.project.navigation.HomeScreenRoutes
import org.example.project.navigation.NavigationItem
import org.example.project.navigation.SearchScreenRoutes
import org.example.project.ui.components.BottomNavigationBar
import org.example.project.ui.screens.HistoryScreen
import org.example.project.ui.screens.PlantInfoScreen
import org.example.project.ui.screens.SearchScreen
import kotlin.time.Clock
/*
fun todaysDate(): String {
    fun LocalDateTime.format() = toString().substringBefore('T')

    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()
    return now.toLocalDateTime(zone).format()
}*/
@OptIn(ExperimentalMaterial3Api::class)

@Composable
//@Preview
fun App() {
    val navController = rememberNavController()
    var selectedTab by rememberSaveable { mutableStateOf(NavigationItem.Home) }
    MaterialTheme {
        }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navItems = NavigationItem.entries,
                onNavItemSelection = {
                    selectedTab = it
                },
                selectedNavItem = selectedTab
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<HomeScreenRoutes> { PlantInfoScreen() }
            composable<SearchScreenRoutes> { SearchScreen() }
            composable<HistoryScreenRoutes> { HistoryScreen() }
        }

    }

    }
