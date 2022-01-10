

package com.arch.template.errors.presenters

import androidx.fragment.app.FragmentActivity
import com.arch.error.presenters.BaseErrorPresenter

interface AndroidErrorPresenter<T : Any> : BaseErrorPresenter<T> {
    fun show(throwable: Throwable, activity: FragmentActivity, data: T)
}
