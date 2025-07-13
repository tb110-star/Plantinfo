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
    @PrimaryKey val id: String,
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
    val isConfirmed: Boolean = false,
    val imageUploadedUrl: String?,
)
fun Suggestions.toPlantHistory(
    combinedId: String,
    serverImageUrl: String?
): PlantHistoryEntity {
    return PlantHistoryEntity(
        id = combinedId,
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
        isConfirmed = true,
        imageUploadedUrl = serverImageUrl
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
    @PrimaryKey val id: String,
    val date: Long = Clock.System.now().toEpochMilliseconds(),
    val healthStatus: String,
    val diseaseName: String? = "Not specified",
    val probability: Double?,
    val description: String = "No description available",

    val classification: String? = null,
    val commonNames: String? = null,
    val chemicalTreatment: String? = null,
    val biologicalTreatment: String? = null,
    val preventionTreatment: String? = null,

    val questionAnswered: String? = null,
    val isConfirmed: Boolean = false,
    val imageUploadedUrl: String?,
)
fun DiseaseSuggestion.toHealthHistory(
    combinedId: String,
    healthStatus: String,
    selectedSuggestion: String? = null,
    serverImageUrl: String?
): HealthHistoryEntity {
    return HealthHistoryEntity(
        id = combinedId,
        healthStatus = healthStatus,
        diseaseName = this.name,
        probability = this.probability,
        description = this.details?.description ?: "No description available",
        classification = this.details?.classification?.takeIf { it.isNotEmpty() }?.joinToString()
            ?: "No classification available",

        commonNames = this.details?.commonName?.takeIf { it.isNotEmpty() }?.joinToString()
            ?: "No common names available",

        chemicalTreatment = this.details?.treatment?.chemical?.takeIf { it.isNotEmpty() }?.joinToString()
            ?: "No chemical treatment available",

        biologicalTreatment = this.details?.treatment?.biological?.takeIf { it.isNotEmpty() }?.joinToString()
            ?: "No biological treatment available",

        preventionTreatment = this.details?.treatment?.prevention?.takeIf { it.isNotEmpty() }?.joinToString()
            ?: "No prevention treatment available",

        questionAnswered = selectedSuggestion,
        isConfirmed = true,
        imageUploadedUrl = serverImageUrl
    )

}

