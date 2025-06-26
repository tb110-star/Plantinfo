package org.example.project.data.local.roomModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.project.data.model.Suggestions


@Entity(tableName = "plantHistory")
data class PlantHistoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val probability: Double,
    val imageUrl: String,
    val description: String,
    val wateringMin: Long?,
    val wateringMax: Long?,
    val bestWatering: String,
    val bestLight: String,
    val bestSoil: String,
    val uses: String
)
fun Suggestions.toPlantHistory(): PlantHistoryEntity {
    return PlantHistoryEntity(
        id = this.id,
        name = this.name,
        probability = this.probability,
        imageUrl = this.details.image.value,
        description = this.details.description.value,
        wateringMin = this.details.watering?.min,
        wateringMax = this.details.watering?.max,
        bestWatering = this.details.bestWatering,
        bestLight = this.details.bestLightCondition,
        bestSoil = this.details.bestSoilType,
        uses = this.details.commonUses
    )
}
/*
LaunchedEffect(Unit) {
    val plantEntity = suggestion.toPlantHistory()
    vM.saveToHistory(plantEntity)
}
//later in details view
 */