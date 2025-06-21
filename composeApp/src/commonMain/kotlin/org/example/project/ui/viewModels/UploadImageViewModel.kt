package org.example.project.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.uploadImage.SharedImage
import org.example.project.data.uploadImage.uploadImageRepository.UploadImageRepository

class UploadImageViewModel(
    private val repo: UploadImageRepository
) : ViewModel() {
    private val _image = MutableStateFlow<SharedImage?>(null)
    val image: StateFlow<SharedImage?> = _image.asStateFlow()

    private val _base64 = MutableStateFlow<String?>(null)
    val base64: StateFlow<String?> = _base64.asStateFlow()

    fun pickFromGallery() {
        viewModelScope.launch {
            val img = repo.pickImageFromGallery()
            _image.value = img
            if (img != null) convertToBase64()
        }
    }

    fun takeFromCamera() {
        viewModelScope.launch {
            val img = repo.takePhotoWithCamera()
            _image.value = img
            if (img != null) convertToBase64()
        }
    }

    fun setImage(image: SharedImage?) {
        _image.value = image
        if (image != null) convertToBase64()
    }

    private fun convertToBase64() {
        val path = _image.value?.localPath ?: return
        viewModelScope.launch {
            val base64 = repo.getBase64(path)
            _base64.value = base64
        }
    }

    fun clear() {
        _image.value = null
        _base64.value = null
    }
}
