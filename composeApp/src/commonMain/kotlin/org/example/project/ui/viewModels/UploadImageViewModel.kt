package org.example.project.ui.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.model.RequestModel
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class UploadImageViewModel : ViewModel() {
    private val _image = MutableStateFlow<ByteArray?>(null)
    val image: StateFlow<ByteArray?> = _image.asStateFlow()    // Holds the selected image

    private val _base64 = MutableStateFlow<String?>(null)
    val base64: StateFlow<String?> = _base64.asStateFlow()    // Holds the Base64 string of the image

    private val _navigateToHealthInfo = MutableStateFlow(false)
    val navigateToHealthInfo = _navigateToHealthInfo.asStateFlow()

    private val _request = MutableStateFlow<RequestModel?>(null)
    val request = _request.asStateFlow()    // Holds the request object

    private val _serverImageUrl = MutableStateFlow<String?>(null)
    val serverImageUrl = _serverImageUrl.asStateFlow()     // Holds the server image URL after upload
    @OptIn(ExperimentalEncodingApi::class)
    // Set image and process to Base64
    fun setImage(byteArray: ByteArray?) {
        _image.value = byteArray
        if (byteArray != null) {
            _base64.value = Base64.encode(byteArray)
            println("setImage called, byteArray=${byteArray?.size}")

        }
    }
    // Create request object from Base64 string
    fun createRequest(base64: String): RequestModel {
        return RequestModel(
            images = listOf(base64),
            latitude = 49.207,
            longitude = 16.608,
            similarImagesS = true
        )
    }
    // Clear all stored data
    fun clear() {
        _image.value = null
        _base64.value = null
        _request.value = null
        _serverImageUrl.value = null
        _navigateToHealthInfo.value = false
    }

}


