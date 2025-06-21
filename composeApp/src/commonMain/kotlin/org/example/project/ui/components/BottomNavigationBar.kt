package org.example.project.ui.components

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.example.project.navigation.NavigationItem


@Composable
fun BottomNavigationBar(
    navItems: List<NavigationItem>,
    onNavItemSelection: (NavigationItem) -> Unit,
    selectedNavItem: NavigationItem,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        navItems.forEach { navItem ->
            val selected = selectedNavItem == navItem
            NavigationBarItem(
                selected = selected,
                onClick = { onNavItemSelection(navItem) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label,
                        tint = if (selected)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                    )
                },
                label = {
                    Text(
                        navItem.label,
                        color = if (selected)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}