package com.arch.presentation.viewmodels.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arch.errors.android.handler.IAndroidExceptionHandler
import com.arch.logger.AppLogger
import com.arch.mediaandroid.AndroidBitmap
import com.arch.mediaandroid.picker.AndroidMediaPickerController
import com.arch.medialibrary.picker.CanceledException
import com.arch.medialibrary.picker.MediaSource
import com.arch.permissions.android.IAndroidPermissionsController
import com.arch.presentation.viewmodels.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val androidMediaPickerController: AndroidMediaPickerController,
    exceptionHandler: IAndroidExceptionHandler,
    permissionHandler: IAndroidPermissionsController,
    logger: AppLogger
) :
    BaseViewModel(exceptionHandler, permissionHandler, logger) {

    private val _selectedImage = MutableLiveData<AndroidBitmap?>()
    val selectedImage: LiveData<AndroidBitmap?> = _selectedImage

    private val _textState = MutableLiveData<String>()
    val textState: LiveData<String> = _textState

    fun onCameraPressed() {
        selectImage(MediaSource.CAMERA)
    }

    fun onGalleryPressed() {
        selectImage(MediaSource.GALLERY)
    }

    @Suppress("TooGenericExceptionCaught")
    fun selectFile() {
        viewModelScope.launch {
            @Suppress("SwallowedException")
            try {
                val file = androidMediaPickerController.pickFiles()
                _textState.value = file.name
            } catch (canceled: CanceledException) {
                _textState.value = "canceled"
            } catch (exc: Exception) {
                _textState.value = exc.toString()
            }
        }
    }

    @Suppress("TooGenericExceptionCaught")
    private fun selectImage(source: MediaSource) {
        viewModelScope.launch {
            @Suppress("SwallowedException")
            try {
                val image = androidMediaPickerController.pickImage(source)
                _selectedImage.value = image
                _textState.value = "image selected"
            } catch (canceled: CanceledException) {
                _textState.value = "canceled"
            } catch (exc: Exception) {
                exc.printStackTrace()
                _selectedImage.value = null
                _textState.value = exc.toString()
            }
        }
    }

}