package com.arch.template.crashlytics

import com.crash_reporting.CrashlyticsLogger

object TemplateCrashlyticsLogger : CrashlyticsLogger() {

    override fun setUserId(userId: String) {
        TODO("Not yet implemented")
    }

    override fun setCustomValue(value: String, forKey: String) {
        TODO("Not yet implemented")
    }

    override fun recordException(throwable: Throwable) {
        TODO("Not yet implemented")
    }

    override fun log(message: String) {
        //FirebaseCrashlytics.getInstance().log(message)
        TODO("Not yet implemented")
    }
}