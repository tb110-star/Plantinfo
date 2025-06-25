package org.example.project.navigation

import kotlinx.serialization.Serializable
import org.example.project.data.model.Suggestions

@Serializable
object HomeScreenRoutes
@Serializable
object SearchScreenRoutes
@Serializable
object HistoryScreenRoutes
@Serializable
object SettingScreenRoutes



@Serializable
data class DetailsPlantRoutes(
    val suggestion: Suggestions
)
