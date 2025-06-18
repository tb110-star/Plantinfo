package org.example.project


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.navigation.HistoryScreenRoutes
import org.example.project.navigation.HomeScreenRoutes
import org.example.project.navigation.NavigationItem
import org.example.project.navigation.SearchScreenRoutes
import org.example.project.navigation.SettingScreenRoutes
import org.example.project.ui.components.BottomNavigationBar
import org.example.project.ui.screens.HistoryScreen
import org.example.project.ui.screens.PlantInfoScreen
import org.example.project.ui.screens.SearchScreen
import org.example.project.ui.screens.SettingsScreen
import org.example.project.ui.theme.AppTheme
import org.example.project.ui.theme.MyThemeColor
import org.example.project.ui.viewModels.ThemeSettingsViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun App() {
    val viewModel: ThemeSettingsViewModel = koinViewModel()
    val settings by viewModel.settings.collectAsState()
    val navController = rememberNavController()
    var selectedTab by rememberSaveable { mutableStateOf(NavigationItem.Home) }
    var darkTheme by remember { mutableStateOf(false) }
    var selectedTheme by remember { mutableStateOf(MyThemeColor.GREEN) }
    AppTheme(
        darkTheme = settings.isDark,
        selectedTheme = settings.theme
    ) {
        Scaffold(
            topBar = {
                val containerColor = MaterialTheme.colorScheme.primary
                val primaryColor = MaterialTheme.colorScheme.primary
                TopAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = primaryColor
                    ),
                    title = { Text(selectedTab.label) },
                    actions = {
                        IconButton(onClick = { navController.navigate(SettingScreenRoutes) }) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }

                )
            },
            bottomBar = {
                BottomNavigationBar(
                    navItems = NavigationItem.entries,
                    onNavItemSelection = {
                        selectedTab = it
                    },
                    selectedNavItem = selectedTab
                )
            }

        ) { innerpading ->
            NavHost(
                navController = navController,
                startDestination = selectedTab.route,
                modifier = Modifier.padding(innerpading)
            ) {
                composable<HomeScreenRoutes> { PlantInfoScreen() }
                composable<SearchScreenRoutes> { SearchScreen() }
                composable<HistoryScreenRoutes> { HistoryScreen() }
                composable<SettingScreenRoutes> { SettingsScreen() }
            }

        }
    }
    }
