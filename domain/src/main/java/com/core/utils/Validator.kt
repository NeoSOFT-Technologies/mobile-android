package com.core.utils

import com.arch.utils.isValidEmail

class Validator {
    companion object Factory {
        fun isEmpty(value: String): Boolean {
            return value.isEmpty()
        }

        fun isValidEmail(value: String): Boolean {
            return value.isValidEmail()
        }
    }
}