package com.arch.errors.android.presenter

import androidx.fragment.app.FragmentActivity
import com.arch.error.presenters.ISelectorErrorPresenter

class SelectorAndroidErrorPresenter constructor(
    private val errorPresenterSelector: (Throwable) -> IAndroidErrorPresenter<String>
) : ISelectorErrorPresenter<String>(errorPresenterSelectorI = errorPresenterSelector),
    IAndroidErrorPresenter<String> {

    override fun show(throwable: Throwable, activity: FragmentActivity, data: String) {
        errorPresenterSelector(throwable).show(throwable, activity, data)
    }
}