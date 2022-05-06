package com.arch.geolocation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Altitude(
    val altitudeMeters: Double,
    val altitudeAccuracyMeters: Double?
) : Parcelable