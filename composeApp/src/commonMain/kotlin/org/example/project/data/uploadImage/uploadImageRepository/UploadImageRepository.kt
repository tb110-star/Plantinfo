package org.example.project.data.uploadImage.uploadImageRepository

import org.example.project.data.uploadImage.SharedImage
import kotlin.io.encoding.Base64


class UploadImageRepository {

    suspend fun pickImageFromGallery(): SharedImage? {
        return platformPickGalleryImage()
    }

    suspend fun takePhotoWithCamera(): SharedImage? {
        return platformTakePhotoWithCamera()
    }
    @OptIn(kotlin.io.encoding.ExperimentalEncodingApi::class)
    suspend fun getBase64(path: String): String {
        val bytes = platformReadImageBytes(path)
        return Base64.encode(bytes)
    }
}
expect suspend fun platformReadImageBytes(path: String): ByteArray
expect suspend fun platformPickGalleryImage(): SharedImage?
expect suspend fun platformTakePhotoWithCamera(): SharedImage?
