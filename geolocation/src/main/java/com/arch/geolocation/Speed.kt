package com.arch.geolocation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Speed(
    val speedMps: Double,
    val speedAccuracyMps: Double?
) : Parcelable