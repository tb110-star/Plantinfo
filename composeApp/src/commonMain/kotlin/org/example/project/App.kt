package org.example.project


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import kotlinx.coroutines.delay
import org.example.project.navigation.*
import org.example.project.ui.components.BottomNavigationBar
import org.example.project.ui.screens.*
import org.example.project.ui.theme.AppTheme
import org.example.project.ui.viewModels.PlantInfoViewModel
import org.example.project.ui.viewModels.ThemeSettingsViewModel
import org.example.project.ui.viewModels.UploadImageViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val plantViewModel: PlantInfoViewModel = koinViewModel()
    val viewModel: ThemeSettingsViewModel = koinViewModel()
    val uploadImageViewModel: UploadImageViewModel = koinViewModel()
    val navigateToHealthInfo by uploadImageViewModel.navigateToHealthInfo.collectAsState()

    val settings by viewModel.settings.collectAsState()
    val navController = rememberNavController()
    var selectedTab by rememberSaveable { mutableStateOf(NavigationItem.Home) }
    var showBackIcon by remember { mutableStateOf(false) }
    var currentTopBarTitle by remember { mutableStateOf("Home") }

    AppTheme(
        darkTheme = settings.isDark,
        selectedTheme = settings.theme
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    title = { Text(text = currentTopBarTitle) },
                    navigationIcon = {
                        if (showBackIcon) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate(SettingScreenRoutes) }) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }
                )
            },
            bottomBar = {
                if (!showBackIcon) {
                    BottomNavigationBar(
                        navItems = NavigationItem.entries,
                        selectedNavItem = selectedTab,
                        onNavItemSelection = { item ->
                            println("BottomNav selected: ${item.label}")

                            selectedTab = item
                            navController.navigate(item.route) {
                                popUpTo(NavigationItem.Home.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            LaunchedEffect(navigateToHealthInfo) {
                println("LaunchedEffect: navigateToHealthInfo = $navigateToHealthInfo")

                if (navigateToHealthInfo) {
                    println("Navigating to HealthInfoScreen ")

                    navController.navigate(NavigationItem.Search.route)
                    delay(150)
                    uploadImageViewModel.resetNavigateToHealthInfo()
                }
            }

            NavHost(
                navController = navController,
                startDestination = NavigationItem.Home.route,
                modifier = Modifier.padding(innerPadding)
            )

            {

                composable<HomeScreenRoutes> {
                    showBackIcon = false
                    currentTopBarTitle = NavigationItem.Home.label
                    PlantInfoScreen(
                      
                        onDetailsClick = { details ->
                            plantViewModel.selectSuggestion(details)
                            navController.navigate("details")

                        }
                    )
                }
                composable<HealthScreenRoutes> {
                    showBackIcon = false
                    currentTopBarTitle = NavigationItem.Search.label
                    HealthInfoScreen()
                }
                composable<HistoryScreenRoutes> {
                    showBackIcon = false
                    currentTopBarTitle = NavigationItem.History.label
                    HistoryScreen()
                }
                composable<SettingScreenRoutes> {
                    showBackIcon = true
                    currentTopBarTitle = "Settings"
                    SettingsScreen()
                }
                composable("details") {
                    showBackIcon = true
                    currentTopBarTitle = "Details Info"
                    val plantViewModel: PlantInfoViewModel = koinViewModel()
                    val suggestion by plantViewModel.selectedSuggestion.collectAsState()

                    if (suggestion != null) {
                        DetailsPlantScreen(
                            suggestion = suggestion!!,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }



            }
        }
    }
}
