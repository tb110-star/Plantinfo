package org.example.project.data.uploadImage

@kotlinx.serialization.Serializable
data class SharedImage(
    val localPath: String,
    val base64: String? = null,

)