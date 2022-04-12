package com.arch.presentation.error.handler


import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.arch.error.handler.BaseExceptionHandler


interface AndroidExceptionHandlerBinder : BaseExceptionHandler {
    fun bind(lifecycleOwner: LifecycleOwner, activity: FragmentActivity)
}

