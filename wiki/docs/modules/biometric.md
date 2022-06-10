# Biometric Authenticator

This page outlines the usage of library for providing Biometry authentication to login user on Android.



## Modules

`biometric` - Includes Android & Kotlin implementation for Biometry authentication

## Features

- **IAndroidBiometryAuthenticator** - Biometry authentication controller to access fingerprint or pattern



## Usage

The `AndroidBiometryAuthenticator` is constructor injected in the viewmodel using HILT.

#### Presentation Layer

```kotlin
   class ExampleViewModel(
    exceptionHandler: IAndroidExceptionHandler,
    permissionHandler: IAndroidPermissionsController,
    logger: AppLogger,
val biometryAuthenticator: IAndroidBiometryAuthenticator) : BaseViewModel(exceptionHandler, permissionHandler, logger) {


   fun openBiometricAuthenticator() {
        viewModelScope.launch {
            try {
                val isSuccess: Boolean =
                    biometryAuthenticator.checkBiometryAuthentication("Just for test", "Oops")
                if (isSuccess) {
                    // action to handle after authentication
                }
            } catch (throwable: Throwable) {
               // action to handler after failure
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
      
      // bind authenticator to lifecycle
	  viewModel.biometryAuthenticator.bind(lifecycle, supportFragmentManager)
      
       // request the information from the corresponding ViewModel
        binding.button.setOnClickListener {
            viewModel.openBiometricAuthenticator()
        }
    }          
}
```

