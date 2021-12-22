package com.arch.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun getDate(date: String?): Date {
        try {
            val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            return fmt.parse(date)
        } catch (e: Exception) {
            return Date()
        }

    }
}
