package com.arch.utils.extension

import java.util.regex.Pattern

fun String?.isValidEmail(): Boolean {
    return this?.let {
        val pattern = Pattern.compile(
            "^([\\p{L}\\d-_.]+){1,64}@([\\p{L}-_.]+){2,255}.[a-z]{2,}$",
            Pattern.CASE_INSENSITIVE
        )
        pattern.matcher(this).matches()
    } ?: kotlin.run { false }
}