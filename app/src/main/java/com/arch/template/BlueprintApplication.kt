package com.arch.template

import android.app.Application
import com.arch.error.mappers.ExceptionMappersStorage
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
                condition = { it is NullPointerException && it.localizedMessage == "200" },
                mapper = { "${resources.getString(R.string.app_error_empty_email)}+MR.strings.myExceptionText.desc()" }
            )
            .register<IllegalArgumentException, String> {
                "MR.strings.illegalArgumentText.desc()"
            }
    }
}