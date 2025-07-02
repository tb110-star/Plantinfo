package org.example.project.data.local.roomModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.project.data.model.Suggestions


@Entity(tableName = "plantHistory")
data class PlantHistoryEntity(
    @PrimaryKey val id: String,
    val name: String?,
    val probability: Double?,
    val imageUrl: String?,
    val description: String = "No description available",
    val wateringMin: Long?,
    val wateringMax: Long?,
    val bestWatering: String?,
    val bestLight: String?,
    val bestSoil: String?,
    val uses: String?
)
fun Suggestions.toPlantHistory(): PlantHistoryEntity {
    return PlantHistoryEntity(
        id = this.id,
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
    )
}
/*
LaunchedEffect(Unit) {
    val plantEntity = suggestion.toPlantHistory()
    vM.saveToHistory(plantEntity)
}
//later in details view
 */