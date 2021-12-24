package com.arch.data.utils

/**
 * A utility class to eliminate nulls in data types coming in from the response.
 */
object NonNullUtils {
    fun getNonNull(string: String?): String {
        return string?.let { it } ?: kotlin.run { "" }
    }

    fun getNonNull(string: Int?): Int {
        return string?.let { it } ?: kotlin.run { 0 }
    }

    fun <T> getNonNull(string: List<T>?): List<T> {
        return string?.let { it } ?: kotlin.run { listOf<T>() }
    }

    fun <K, V> getNonNull(hashMap: Map<K, V>?): Map<K, V> {
        return hashMap?.let { it } ?: kotlin.run { hashMapOf<K, V>() }
    }

    fun getNonNull(string: Long?): Long {
        return string?.let { it } ?: kotlin.run { 0L }
    }

    fun getNonNull(string: Float?): Float {
        return string?.let { it } ?: kotlin.run { 0f }
    }

    fun getNonNull(string: Double?): Double {
        return string?.let { it } ?: kotlin.run { 0.0 }
    }

    fun getNonNull(string: Boolean?): Boolean {
        return string?.let { it } ?: kotlin.run { false }
    }
}
