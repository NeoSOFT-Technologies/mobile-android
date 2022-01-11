package com.arch.template

import android.app.Application
import com.arch.template.utils.AppExceptionMapper
import com.arch.template.utils.CrashlyticsLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BlueprintApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppExceptionMapper.init(this)
        CrashlyticsLogger.init(this)
    }
}