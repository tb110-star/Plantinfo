package org.example.project.ui.components

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.example.project.navigation.NavigationItem


@Composable
fun BottomNavigationBar(
    navItems: List<NavigationItem>,
    onNavItemSelection: (NavigationItem) -> Unit,
    selectedNavItem: NavigationItem,
) {
    NavigationBar {
        navItems.forEach { navItem ->
            NavigationBarItem(
                selected = selectedNavItem == navItem,
                onClick = { onNavItemSelection(navItem) },
                icon = { Icon(navItem.icon, contentDescription = navItem.label) },
                label = { Text(navItem.label) }
            )
        }
    }
}
