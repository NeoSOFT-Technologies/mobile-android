package com.arch.presentation.model

data class ErrorModel(
    val message: String,
    val code: String,
    val requestId: String?
)