package com.core.usecase

interface Params {

    fun verify(): Boolean

    fun getVerificationErrorMessage(): String
}
