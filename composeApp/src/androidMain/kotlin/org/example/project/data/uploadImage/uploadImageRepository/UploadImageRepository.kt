package org.example.project.data.uploadImage.uploadImageRepository

import org.example.project.data.uploadImage.SharedImage
import java.io.File
import android.content.Context

actual suspend fun platformPickGalleryImage(): SharedImage? {
    return null // TODO: implement
}

actual suspend fun platformTakePhotoWithCamera(): SharedImage? {
    return null // TODO: implement
}
actual suspend fun platformReadImageBytes(path: String): ByteArray {
    val file = File(path)
    return file.readBytes()
}