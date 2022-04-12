package com.arch.usecase

interface Params {

    fun verify(): Boolean

    fun getVerificationErrorMessage(): String
}
