package org.example.project.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlantIdentificationResult(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("model_version")
    val modelVersion: String,
    @SerialName("input")
    val input: Input,
    @SerialName("result")
    val result: Result,
    @SerialName("status")
    val status: String,
)
@Serializable
data class Input(
    val latitude: Double,
    val longitude: Double,
    val similarImages: Boolean,
    val images: List<String>,
    val datetime: String,
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
    val similarImages: List<SimilarImage>,
    val details: Details,
)
@Serializable

data class SimilarImage(
    val id: String,
    val url: String,
    @SerialName("license_name")
    val licenseName: String,
    @SerialName("license_url")
    val licenseUrl: String,
    val citation: String,
    val similarity: Double,
    @SerialName("url_small")
    val urlSmall: String,
)
@Serializable

data class Details(
    @SerialName("common_names")
    val commonNames: List<String>?,
    val taxonomy: Taxonomy,
    val url: String,
    @SerialName("gbif_id")
    val gbifId: Long,
    @SerialName("inaturalist_id")
    val inaturalistId: Long,
    val rank: String,
    val description: Description,
    val synonyms: List<String>,
    val image: Image,
    @SerialName("edible_parts")
    val edibleParts: List<String>?,
    val watering: Watering?,
    @SerialName("best_light_condition")
    val bestLightCondition: String,
    @SerialName("best_soil_type")
    val bestSoilType: String,
    @SerialName("common_uses")
    val commonUses: String,
    @SerialName("cultural_significance")
    val culturalSignificance: String,
    val toxicity: String,
    @SerialName("best_watering")
    val bestWatering: String,
    val language: String,
    @SerialName("entity_id")
    val entityId: String,
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
    val citation: String,
    @SerialName("license_name")
    val licenseName: String,
    @SerialName("license_url")
    val licenseUrl: String,
)
@Serializable

data class Image(
    val value: String,
    val citation: String,
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
