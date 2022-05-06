package com.arch.geolocation

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.arch.permissions.IPermissionsController
import kotlinx.coroutines.flow.Flow

interface GeoLocationTracker {
    val permissionsController: IPermissionsController
    var isStarted: Boolean
    suspend fun startTracking() // can be suspended for request permission
    fun stopTracking()

    fun getLocationsFlow(): Flow<LatLng>

    fun getExtendedLocationsFlow(): Flow<ExtendedLocation>
}

interface AndroidGeoLocationTracker : GeoLocationTracker {
    fun bind(lifecycle: Lifecycle, context: Context, fragmentManager: FragmentManager)
}