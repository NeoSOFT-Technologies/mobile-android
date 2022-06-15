package com.arch.presentation.viewmodels.geolocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arch.errors.android.handler.IAndroidExceptionHandler
import com.arch.geolocation.AndroidGeoLocationTracker
import com.arch.geolocation.LatLng
import com.arch.logger.AppLogger
import com.arch.permissions.android.IAndroidPermissionsController
import com.arch.presentation.viewmodels.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeoLocationViewModel @Inject constructor(
    exceptionHandler: IAndroidExceptionHandler,
    permissionHandler: IAndroidPermissionsController,
    logger: AppLogger,
    val geoLocationTracker: AndroidGeoLocationTracker,
) :
    BaseViewModel(exceptionHandler, permissionHandler, logger) {

    private val _textLocation: MutableLiveData<String> = MutableLiveData("no data")
    val textLocation: LiveData<String> = _textLocation

    private val _textExtendedLocation: MutableLiveData<String> = MutableLiveData("no data")
    val textExtendedLocation: LiveData<String> = _textExtendedLocation

    private val _speed: MutableLiveData<String> = MutableLiveData("no data")
    val speed: LiveData<String> = _speed

    private val _latLng: MutableLiveData<LatLng> = MutableLiveData(LatLng(0.0, 0.0))
    val latLng: LiveData<LatLng> = _latLng

    init {
        viewModelScope.launch {
            geoLocationTracker.getLocationsFlow()
                .distinctUntilChanged()
                .collect {
                    _latLng.value = it
                    _textLocation.value = it.toString()
                }
        }

        viewModelScope.launch {
            geoLocationTracker.getExtendedLocationsFlow()
                .distinctUntilChanged()
                .collect {
                    _textExtendedLocation.value = """
                        locationAccuracy=${it.location.coordinatesAccuracyMeters}
                        
                        ${it.altitude}
                        
                        ${it.azimuth}
                        
                        ${it.speed}
                        
                        timestamp=${it.timestampMs}
                    """.trimIndent()

                    _speed.value = """ = ${it.speed.speedMps} "Mps""".trimIndent()

                    logger.d("Location!! ${_textExtendedLocation.value}")
                }
        }


    }

    fun toggleLocationRequest() {
        if (geoLocationTracker.isStarted) {
            geoLocationTracker.stopTracking()
        } else {
            viewModelScope.launch {
                try {
                    geoLocationTracker.startTracking()
                } catch (exc: Throwable) {
                    _textLocation.value = exc.toString()
                    _textExtendedLocation.value = exc.toString()
                }
            }
        }
    }

}