package com.arch.error

sealed class AppErrorType {
    object EmailEmpty : AppErrorType()
    object PasswordEmpty : AppErrorType()
    object InvalidEmail : AppErrorType()
    object None : AppErrorType()
}