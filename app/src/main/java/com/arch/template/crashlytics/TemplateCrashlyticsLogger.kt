package com.arch.template.crashlytics

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.reporting.CrashlyticsLogger

object TemplateCrashlyticsLogger : CrashlyticsLogger() {

    override fun setUserId(userId: String) {
        FirebaseCrashlytics.getInstance().setUserId(userId)
    }

    override fun setCustomValue(value: String, forKey: String) {
        FirebaseCrashlytics.getInstance().setCustomKey(forKey, value)
    }

    override fun recordException(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }

    override fun log(message: String) {
        FirebaseCrashlytics.getInstance().log(message)
    }
}