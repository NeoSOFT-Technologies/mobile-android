package com.arch.geolocation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Azimuth(
    val azimuthDegrees: Double,
    val azimuthAccuracyDegrees: Double?
) : Parcelable