package org.example.project

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.example.project.data.di.RoomFactory
import org.example.project.data.local.ThemeSettingRepository.appContext
import org.example.project.data.di.initKoin
import org.example.project.ui.screens.WelcomeScreen
import org.example.project.utils.initJsonLoader
import org.example.project.utils.loadJsonFromResources

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        appContext = applicationContext
        super.onCreate(savedInstanceState)

        initJsonLoader(applicationContext)
        lifecycleScope.launch {
            val json = loadJsonFromResources("mock_plant_response.json")
            Log.d("JSONTest",json)
        }

        val factory = RoomFactory(application)
        initKoin(factory)
        setContent {
            var showWelcome by remember { mutableStateOf(true) }


            if (showWelcome) {
                WelcomeScreen(
                    onStartClick = { showWelcome = false }
                )
            } else {
                App()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}