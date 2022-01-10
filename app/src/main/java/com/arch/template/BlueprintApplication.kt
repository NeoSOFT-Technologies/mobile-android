package com.arch.template

import android.app.Application
import com.arch.template.utils.AppExceptionMapper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BlueprintApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppExceptionMapper.init(this)
    }
}