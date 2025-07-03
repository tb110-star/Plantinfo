package org.example.project.data.model
import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class PlantIdentificationResult(
   @SerialName("input")
   val input: Input,
   @SerialName("result")
   val result: Result,
)

@Serializable
data class Input(

    val images: List<String>,
)


@Serializable
data class Result(
    @SerialName("is_plant")
    val isPlant: IsPlant,
    val classification: Classification,
)
@Serializable
data class IsPlant(
    val probability: Double,
    val threshold: Double,
    val binary: Boolean,
)
@Serializable
data class Classification(
    val suggestions: List<Suggestions>,
)
@Serializable
data class Suggestions(
    val id: String,
    val name: String,
    val probability: Double,
    @SerialName("similar_images")
    val similarImages: List<SimilarImage> = emptyList(),
    val details: Details,
)
@Serializable
data class SimilarImage(
    val id: String? = null,
    val url: String? = null,
    @SerialName("license_name")
    val licenseName: String? = null,
    @SerialName("license_url")
    val licenseUrl: String? = null,
    val citation: String? = null,
    val similarity: Double? = null,
    @SerialName("url_small")
    val urlSmall: String? = null,
)
@Serializable
data class Details(
    @SerialName("common_names")
    val commonNames: List<String>? = null,
    val taxonomy: Taxonomy? = null,
    val url: String? = null,
    @SerialName("gbif_id")
    val gbifId: Long? = null,
    @SerialName("inaturalist_id")
    val inaturalistId: Long? = null,
    val rank: String? = null,
    val description: Description? = null,
    val synonyms: List<String>? = null,
    val image: Image? = null,
    @SerialName("edible_parts")
    val edibleParts: List<String>?,
    val watering: Watering? = null,
    @SerialName("best_light_condition")
    val bestLightCondition: String? = null,
    @SerialName("best_soil_type")
    val bestSoilType: String? = null,
    @SerialName("common_uses")
    val commonUses: String? = null,
    @SerialName("cultural_significance")
    val culturalSignificance: String? = null,
    val toxicity: String? = null,
    @SerialName("best_watering")
    val bestWatering: String? = null,
    val language: String? = null,
    @SerialName("entity_id")
    val entityId: String? = null,
)
@Serializable

data class Taxonomy(
    @SerialName("class")
    val class_field: String,
    val genus: String,
    val order: String,
    val family: String,
    val phylum: String,
    val kingdom: String,
)
@Serializable

data class Description(
    val value: String,
    val citation: String? = null,
    @SerialName("license_name")
    val licenseName: String,
    @SerialName("license_url")
    val licenseUrl: String,
)
@Serializable

data class Image(
    val value: String,
    val citation: String? = null,
    @SerialName("license_name")
    val licenseName: String,
    @SerialName("license_url")
    val licenseUrl: String,
)
@Serializable

data class Watering(
    val max: Long,
    val min: Long,
)
