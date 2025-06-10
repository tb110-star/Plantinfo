
package org.example.project.utils

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private lateinit var appContext: Context

fun initJsonLoader(context: Context) {
    appContext = context.applicationContext
}

actual suspend fun loadJsonFromResources(filename: String): String = withContext(Dispatchers.IO) {
    val input = appContext.assets.open("$filename")
    val bytes = input.readBytes()
    input.close()
    bytes.decodeToString()
}

