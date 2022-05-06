package com.arch.geolocation.di

import com.arch.geolocation.AndroidGeoLocationTracker
import com.arch.geolocation.GeoLocationTracker
import com.arch.geolocation.android.GeoLocationTrackerImpl
import com.arch.permissions.android.IAndroidPermissionsController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocationModule {

    @Provides
    fun providesGeoLocationTracker(
        iAndroidPermissionsController: IAndroidPermissionsController,
    ): AndroidGeoLocationTracker =
        GeoLocationTrackerImpl(
            permissionsController = iAndroidPermissionsController
        )
}