package com.arch.template

import android.app.Application
import com.arch.error.mappers.ExceptionMappersStorage
import com.core.error.NetworkError
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BlueprintApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initExceptionStorage()
    }

    @Suppress("MagicNumber")
    fun initExceptionStorage() {
        ExceptionMappersStorage
            .condition<String>(
                condition = { it is NetworkError },
                mapper = {
                    (it as NetworkError).message ?: ""
                }
            )
            .register<IllegalArgumentException, String> {
                "MR.strings.illegalArgumentText.desc()"
            }
    }
}