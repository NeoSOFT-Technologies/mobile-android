# Runtime Permission Handler

This page outlines the usage of library for providing runtime permission on Android.



## Modules

`permissions` - Includes pure Kotlin implementation of Runtime Permission Handler

`permissions-android` - Includes android counter part implementation to support Runtime Permission Handler with Android Lifecycles



## Features

- **Permission** - Enumeration with primary types of device permissions
- **IPermissionsController** - Handler for runtime permission requests can be used in the common code with lifecycle safety for Android
- **PermissionDeniedException** and **PermissionDeniedAlwaysException** - Exceptions to handle user denial of permissions



## List of supported permissions

- The full list can be found in `com.arch.permissions.Permission` enum.
  - Camera: **Permission.CAMERA**
  - Gallery: **Permission.GALLERY**
  - Storage read: **Permission.STORAGE**
  - Storage write: **Permission.WRITE_STORAGE**
  - Fine location: **Permission.LOCATION**
  - Coarse location: **Permission.COARSE_LOCATION**
  - Remote notifications: **Permission.REMOTE_NOTIFICATION**
  - Audio recording: **Permission.RECORD_AUDIO**



## Usage

The AndrioidPermissionController is constructor injected in the viewmodel using HILT.

#### Presentation Layer

```kotlin
   class ExampleViewModel(
    exceptionHandler: IAndroidExceptionHandler,
    permissionHandler: IAndroidPermissionsController,
    logger: AppLogger) : BaseViewModel(exceptionHandler, permissionHandler, logger) {
    fun requestForGalleryPermission() {
        viewModelScope.launch {
						exceptionHandler.handle { // Automatic Exception Handler
                permissionsController.providePermission(Permission.GALLERY)
                // Permission has been granted successfully.
            } .catch<Exception> {
                val handled: Boolean = when (it) {
                    
                    is PermissionDeniedAlwaysException -> { 
                        // Permission is always denied.
                        // show rational in activity by callback

                        // we tell the exceptionHandler that
                        // we are handling the error by our own & return
                        // true to tell the system to ignore
                        true
                    }
                    
                    is PermissionDeniedException -> {
                      	// Permission was denied.
                      	// show system level default error
                        false
                    }
                    else -> false // here the system is instructed to handle the error
                }
                handled
            }.execute()
        }
    }
}  
```



#### App/UI Layer                   

```kotlin
class ExampleActivity : BaseActivity<ActivityResourceBinding, ExampleViewModel>() {
  	// Here the Baseactivity already binds the permission handler to activity lifecycle.
  
    override val viewModel by viewModels<ExampleViewModel>()

    override fun getLayoutRes() = R.layout.activity_example

    override fun initViewModel(viewModel: ExampleViewModel) {
        binding.viewModel = viewModel
        
       // request the permission from the corresponding viewmodel
        binding.button.setOnClickListener {
            viewModel.requestForGalleryPermission()
        }
    }          
}
```
