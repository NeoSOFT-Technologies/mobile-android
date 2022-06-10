# Media Selection

This page outlines the usage of library for providing Media selection & presenting for mobile android.



## Modules

`media-android` - Includes Android & Kotlin implementation for Media Picker Selection
`media-library` - Includes Pure Kotlin implementation for Media Picker.

## Features

- **AndroidMediaPickerController** - Controller used to access media files via camera or gallery or file explorer.



## Usage

The `AndroidMediaPickerController` is constructor injected in the ViewModel using HILT.

#### Presentation Layer

```kotlin
   class ExampleViewModel(
    val mediaController: AndroidMediaPickerController,
    exceptionHandler: IAndroidExceptionHandler,
    permissionHandler: IAndroidPermissionsController,
    logger: AppLogger) : BaseViewModel(exceptionHandler, permissionHandler, logger) {
    
    //Select file from Camera
    fun onSelectPhotoPressed() {
        viewModelScope.launch {
            try {
                val bitmap = mediaController.pickImage(MediaControllerSource.CAMERA)
                // captured photo in bitmap
            } catch(_: CanceledException) {
                // cancel capture
            } catch(error: Throwable) {
                // denied permission or file read error
            }
        }
    }
   
    //Select file from Gallery
    fun onSelectPhotoPressedFromGallery() {
        viewModelScope.launch {
            try {
                val bitmap = mediaController.pickImage(MediaControllerSource.GALLERY)
                // captured photo in bitmap
            } catch(_: CanceledException) {
                // cancel capture
            } catch(error: Throwable) {
                // denied permission or file read error
            }
        }
    }

    //Selecting file from explorer
    fun selectFile() {
        viewModelScope.launch {
            try {
                val file = androidMediaPickerController.pickFiles()
                // get file path & name
                _textState.value = file.name
            } catch (canceled: CanceledException) {
                // error
            } catch (exc: Exception) {
               // error
            }
        }
    }
}  
```



#### App/UI Layer

```kotlin
class ExampleActivity : BaseActivity<ActivityExampleBinding, ExampleViewModel>() {
  
    override val viewModel by viewModels<ExampleViewModel>()

    override fun getLayoutRes() = R.layout.activity_example

    override fun initViewModel(viewModel: ExampleViewModel) {
        binding.viewModel = viewModel

        viewModel.mediaController.bind(lifecycle, supportFragmentManager) // permissionController bind automatically
      
       // request the information from the corresponding ViewModel
        binding.button.setOnClickListener {
            viewModel.onSelectPhotoPressed()
        }
    }          
}
```

