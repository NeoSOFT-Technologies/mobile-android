package com.arch.geolocation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Location(
    val coordinates: LatLng,
    val coordinatesAccuracyMeters: Double
) : Parcelable