package com.arch.errors.android.handler

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.arch.error.handler.IExceptionHandler

interface IAndroidExceptionHandler : IExceptionHandler {
    fun bind(lifecycleOwner: LifecycleOwner, activity: FragmentActivity)
}