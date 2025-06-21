package org.example.project.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.ui.theme.MyThemeColor
import org.example.project.ui.viewModels.ThemeSettingsViewModel
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SettingsScreen(
    viewModel: ThemeSettingsViewModel = koinViewModel()
) {
    val settings by viewModel.settings.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Dark mode")
            Switch(
                checked = settings.isDark,
                onCheckedChange = { viewModel.setDark(it) }
            )
        }
        Spacer(Modifier.padding(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Theme color")
            Button(onClick = { expanded = true }) {
                Text(settings.theme.name)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                MyThemeColor.values().forEach { color ->
                    DropdownMenuItem(onClick = {
                        viewModel.setTheme(color)
                        expanded = false
                    }) {
                        Text(text = color.name)
                    }
                }
            }
        }
    }
}