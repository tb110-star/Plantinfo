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

class UploadImageViewModel(
) : ViewModel(), CoroutineScope by MainScope()  {
    private val _image = MutableStateFlow<SharedImage?>(null)
    val image: StateFlow<SharedImage?> = _image.asStateFlow()
    private val _base64 = MutableStateFlow<String?>(null)
    val base64: StateFlow<String?> = _base64.asStateFlow()
    private val _navigateToHealthInfo = MutableStateFlow(false)
    val navigateToHealthInfo = _navigateToHealthInfo.asStateFlow()
    private val _request = MutableStateFlow<RequestModel?>(null)
    val request = _request.asStateFlow()
    private val _serverImageUrl = MutableStateFlow<String?>(null)
    val serverImageUrl = _serverImageUrl.asStateFlow()

    fun setServerImageUrl(url: String?) {
        _serverImageUrl.value = url
    }


    fun setRequest(req: RequestModel) {
        _request.value = req
    }
    fun triggerNavigateToHealthInfo() {
        println(" triggerNavigateToHealthInfo called")

        _navigateToHealthInfo.value = true
    }

    fun resetNavigateToHealthInfo() {
        println(" resetNavigateToHealthInfo called")

        _navigateToHealthInfo.value = false
    }
    fun setImage(image: SharedImage?) {
        _image.value = image
        if (image != null) {
            onImageSelected(image)
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun onImageSelected(sharedImage: SharedImage) {
        viewModelScope.launch {
            val byteArray = sharedImage.toByteArray()
            _base64.value = byteArray?.let { Base64.encode(it) }
        }
    }
    fun clear() {
        _image.value = null
        _base64.value = null
    }
    fun createRequest(base64: String): RequestModel {
        return RequestModel(
            images = listOf(base64),
            latitude = 49.207,
            longitude = 16.608,
            similarImagesS = true
        )
    }

}

