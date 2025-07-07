package shared

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream
import shared.BitmapUtils.resizeBitmap
actual class SharedImage(private val bitmap: android.graphics.Bitmap?) {

    actual fun toByteArray(): ByteArray? {
        return if (bitmap != null) {
            val resizedBitmap = resizeBitmap(bitmap, 1024)

            val byteArrayOutputStream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, byteArrayOutputStream)

            val byteArray = byteArrayOutputStream.toByteArray()

            byteArray
        } else {
            println("toByteArray null")
            null
        }
    }


    actual fun toImageBitmap(): ImageBitmap? {
        val byteArray = toByteArray()
        return if (byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()
        } else {
            println("toImageBitmap null")
            null
        }
    }
}