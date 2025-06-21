package org.example.project.data.uploadImage.uploadImageRepository

import org.example.project.data.uploadImage.SharedImage
import platform.Foundation.NSData
import platform.Foundation.dataWithContentsOfFile
import kotlinx.cinterop.*
import platform.Foundation.*
actual suspend fun platformPickGalleryImage(): SharedImage? {
    return null // TODO: implement
}

actual suspend fun platformTakePhotoWithCamera(): SharedImage? {
    return null // TODO: implement
}


@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
actual suspend fun platformReadImageBytes(path: String): ByteArray {
    val nsData: NSData? = NSData.dataWithContentsOfFile(path)
    return nsData?.let { data ->
        val size = data.length.toInt()
        val byteArray = ByteArray(size)
        byteArray.usePinned { pinned ->
            data.getBytes(pinned.addressOf(0), size.toULong())
        }
        byteArray
    } ?: ByteArray(0)
}