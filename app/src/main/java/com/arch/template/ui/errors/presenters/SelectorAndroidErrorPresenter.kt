package com.arch.template.ui.errors.presenters

import androidx.fragment.app.FragmentActivity
import com.arch.error.presenters.BaseSelectorErrorPresenter
import com.arch.presentation.error.presenter.AndroidErrorPresenter

class SelectorAndroidErrorPresenter constructor(
    private val errorPresenterSelector: (Throwable) -> AndroidErrorPresenter<String>
) : BaseSelectorErrorPresenter<String>(errorPresenterSelector = errorPresenterSelector),
    AndroidErrorPresenter<String> {

    override fun show(throwable: Throwable, activity: FragmentActivity, data: String) {
        errorPresenterSelector(throwable).show(throwable, activity, data)
    }
}