package com.arch.errors.android.presenter

import androidx.fragment.app.FragmentActivity
import com.arch.error.presenters.IErrorPresenter

interface IAndroidErrorPresenter<T : Any> : IErrorPresenter<T> {
    fun show(throwable: Throwable, activity: FragmentActivity, data: T)
}