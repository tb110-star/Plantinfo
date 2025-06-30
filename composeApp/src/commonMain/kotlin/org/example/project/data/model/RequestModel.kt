package org.example.project.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestModel(
    val images: List<String>,
    val latitude: Double,
    val longitude: Double,
    @SerialName("similar_images")
    val similarImagesS: Boolean = true
)