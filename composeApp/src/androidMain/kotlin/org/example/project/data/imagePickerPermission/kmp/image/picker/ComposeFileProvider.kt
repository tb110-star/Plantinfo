package kmp.image.picker


import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

class ComposeFileProvider : FileProvider()

object ImageUriHelper {
    fun createTempImageUri(context: Context): Uri {
        val tempFile = File.createTempFile(
            "picture_${System.currentTimeMillis()}",
            ".jpg",
            context.cacheDir
        ).apply {
            createNewFile()
        }

        val authority = "${context.packageName}.provider"
        return FileProvider.getUriForFile(
            context,
            authority,
            tempFile
        )
    }
}
