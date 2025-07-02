package org.example.project.data.local.roomModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.benasher44.uuid.uuid4
import kotlinx.datetime.Clock
import org.example.project.data.model.DiseaseSuggestion
import org.example.project.data.model.HealthAssessmentResponse
import org.example.project.data.model.Suggestions



@Entity(tableName = "plantHistory")
data class PlantHistoryEntity(
    @PrimaryKey val id: String = uuid4().toString(),
    val date: Long = Clock.System.now().toEpochMilliseconds(),
    val name: String?,
    val probability: Double?,
    val imageUrl: String?,
    val description: String = "No description available",
    val wateringMin: Long?,
    val wateringMax: Long?,
    val bestWatering: String? = "Not specified",
    val bestLight: String? = "Not specified",
    val bestSoil: String? = "Not specified",
    val uses: String ?= "Not specified",
    val isConfirmed: Boolean = false
)
fun Suggestions.toPlantHistory(): PlantHistoryEntity {
    return PlantHistoryEntity(

        name = this.name,
        probability = this.probability,
        imageUrl = this.details.image?.value ?: "",
        description = this.details.description?.value ?: "No description available",
        wateringMin = this.details.watering?.min,
        wateringMax = this.details.watering?.max,
        bestWatering = this.details.bestWatering ?: "Not specified",
        bestLight = this.details.bestLightCondition ?: "Not specified",
        bestSoil = this.details.bestSoilType ?: "Not specified",
        uses = this.details.commonUses ?: "Not specified",
        isConfirmed = true
    )
}
/*
LaunchedEffect(Unit) {
    val plantEntity = suggestion.toPlantHistory()
    vM.saveToHistory(plantEntity)
}
//later in details view
 */

@Entity(tableName = "health_history")
data class HealthHistoryEntity(
    @PrimaryKey val id: String = uuid4().toString(),
    val date: Long = Clock.System.now().toEpochMilliseconds(),
    val healthStatus: String,
    val diseaseName: String? = "Not specified",
    val probability: Double?,
    val description: String = "No description available",
    val questionAnswered: String? = null,
    val isConfirmed: Boolean = false
)
fun DiseaseSuggestion.toHealthHistory(
    healthStatus: String,
    selectedSuggestion: String? = null
): HealthHistoryEntity {
    return HealthHistoryEntity(
        healthStatus = healthStatus,
        diseaseName = this.name,
        probability = this.probability,
        description = this.details?.description ?: "No description available",
        questionAnswered = selectedSuggestion,
        isConfirmed = true
    )
}

