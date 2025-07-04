package org.example.project.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.model.RequestModel
import shared.SharedImage
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class UploadImageViewModel : ViewModel() {
    private val _image = MutableStateFlow<SharedImage?>(null)
    val image: StateFlow<SharedImage?> = _image.asStateFlow()    // Holds the selected image

    private val _base64 = MutableStateFlow<String?>(null)
    val base64: StateFlow<String?> = _base64.asStateFlow()    // Holds the Base64 string of the image

    private val _navigateToHealthInfo = MutableStateFlow(false)
    val navigateToHealthInfo = _navigateToHealthInfo.asStateFlow()

    private val _request = MutableStateFlow<RequestModel?>(null)
    val request = _request.asStateFlow()    // Holds the request object

    private val _serverImageUrl = MutableStateFlow<String?>(null)
    val serverImageUrl = _serverImageUrl.asStateFlow()     // Holds the server image URL after upload

    // Set image and process to Base64
    fun setImage(image: SharedImage?) {
        _image.value = image
        if (image != null) {
            onImageSelected(image)
        }
    }
    // Convert image to Base64 string
    @OptIn(ExperimentalEncodingApi::class)
    private fun onImageSelected(sharedImage: SharedImage) {
        viewModelScope.launch {
            val byteArray = sharedImage.toByteArray()
            _base64.value = byteArray?.let { Base64.encode(it) }
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

