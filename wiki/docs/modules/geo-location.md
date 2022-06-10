# Geo-Location Tracker

This page outlines the usage of library for providing Geolocation to track user on Android.



## Modules

`geolocation` - Includes Android & Kotlin implementation to track user geolocation.

## Features

- **Geolocation tracking** - track user location from common module.
- `distinctUntilChanged()` gives continous location information when subscribed
- If you need only current & one time location for the user just use `first()` value from the `Flow` subscribed to listen with `distinctUntilChanged()`

  

## Usage

The `AndroidGeoLocationTracker` is constructor injected in the viewmodel using HILT.

#### Presentation Layer

```kotlin
   class ExampleViewModel(
    exceptionHandler: IAndroidExceptionHandler,
    permissionHandler: IAndroidPermissionsController,
    logger: AppLogger,
    val geoLocationTracker: AndroidGeoLocationTracker,) : ObservableBaseViewModel(exceptionHandler, permissionHandler, logger) {

      init {
        
        viewModelScope.launch {
            geoLocationTracker.getLocationsFlow()
                .distinctUntilChanged() 
                .collect { println("new location: $it") }
        }
        
          viewModelScope.launch {
            geoLocationTracker.getExtendedLocationsFlow()
                .distinctUntilChanged()
                .collect {
                    val locationInformation = """
                        locationAccuracy=${it.location.coordinatesAccuracyMeters}
                        
                        ${it.altitude}
                        
                        ${it.azimuth}
                        
                        ${it.speed}
                        
                        timestamp=${it.timestampMs}
                    """.trimIndent()
                    logger.d("Location!! ${locationInformation}")
                }
        }
    }
     
     
    fun onStartPressed() {
        viewModelScope.launch { geoLocationTracker.startTracking() }
    }

    fun onStopPressed() {
        geoLocationTracker.stopTracking()
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
      
      // bind tracker to lifecycle
	  viewModel.locationTracker.bind(lifecycle, this, supportFragmentManager)
      
       // request the information from the corresponding viewmodel
        binding.button.setOnClickListener {
            viewModel.onStartPressed() or viewModel.onStopPressed()
        }
    }          
}
```

