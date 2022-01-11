package com.arch.template.utils

import android.content.Context
import com.arch.template.BuildConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

object CrashlyticsLogger {
    fun init(context: Context) {
        try {
            FirebaseApp.initializeApp(
                context, FirebaseOptions.Builder()
                    .setApplicationId(BuildConfig.MOBILE_SDK_APP_ID)
                    .setApiKey(BuildConfig.CURRENT_KEY)
                    .setDatabaseUrl(BuildConfig.FIREBASE_URL)
                    .setGcmSenderId(BuildConfig.PROJECT_NUMBER)
                    .setStorageBucket(BuildConfig.STORAGE_BUCKET)
                    .setProjectId(BuildConfig.PROJECT_ID)
                    .build()
            )
        } catch (e: Exception) {
            MyAppLogger.d(e)
        }
    }
}