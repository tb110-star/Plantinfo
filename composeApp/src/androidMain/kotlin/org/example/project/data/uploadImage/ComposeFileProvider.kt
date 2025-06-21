package org.example.project.data.uploadImage

// مسیر: androidMain/org/example/project/data/uploadImage/ComposeFileProvider.kt


import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import org.example.project.R
import java.io.File
import java.util.*

class ComposeFileProvider : FileProvider(
    R.xml.provider_paths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val tempFile = File.createTempFile(
                "picture_${System.currentTimeMillis()}",
                ".png",
                context.cacheDir
            ).apply {
                createNewFile()
            }
            val authority = context.applicationContext.packageName + ".provider"
            return getUriForFile(
                Objects.requireNonNull(context),
                authority,
                tempFile,
            )
        }
    }
}