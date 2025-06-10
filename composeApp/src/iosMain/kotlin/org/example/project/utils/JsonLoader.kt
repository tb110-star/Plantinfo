
package org.example.project.utils

import platform.Foundation.*

actual suspend fun loadJsonFromResources(filename: String): String {
    val path = NSBundle.mainBundle.pathForResource(name = filename, ofType = null)
    val data = NSData.dataWithContentsOfFile(path!!)
        ?: error("Could not load resource: $filename")
    val str = NSString.create(data, NSUTF8StringEncoding) as String
    return str
}