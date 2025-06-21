package org.example.project.data.uploadImage.uploadImageRepository
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.Foundation.*

fun saveImageToDocuments(image: UIImage): String? {
    val data = UIImageJPEGRepresentation(image, 0.9) ?: return null
    val fileManager = NSFileManager.defaultManager
    val urls = fileManager.URLsForDirectory(
        directory = NSDocumentDirectory,
        inDomains = NSUserDomainMask
    )
    val documentsDir = urls.firstOrNull() as? NSURL ?: return null

    val filename = "plant_${NSDate().timeIntervalSince1970}.jpg"
    val fileURL = documentsDir.URLByAppendingPathComponent(filename)

    data.writeToURL(fileURL!!, atomically = true)
    return fileURL!!.absoluteString
}
