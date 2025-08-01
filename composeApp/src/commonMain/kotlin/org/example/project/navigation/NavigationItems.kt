package org.example.project.navigation
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home

enum class NavigationItem(
    val route: Any,

    val label: String,
    val icon: ImageVector
)
{
    Home(HomeScreenRoutes, "Home", Icons.Default.Home),
   // Health(HealthScreenRoutes, "Health", Icons.Default.Healing),
    History(HistoryScreenRoutes, "History", Icons.Default.History)
}