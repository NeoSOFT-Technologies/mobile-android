/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.arch.template.errors.presenters

import androidx.fragment.app.FragmentActivity

interface ErrorPresenter<T : Any> {
    fun show(throwable: Throwable, activity: FragmentActivity, data: T)
}
