package com.arch.geolocation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ExtendedLocation(
    val location: Location,
    val azimuth: Azimuth,
    val speed: Speed,
    val altitude: Altitude,
    val timestampMs: Long
) : Parcelable