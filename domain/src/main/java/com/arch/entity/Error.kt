package com.arch.entity

data class Error(
    val message: String,
    val code: String,
    val requestId: String?
)
