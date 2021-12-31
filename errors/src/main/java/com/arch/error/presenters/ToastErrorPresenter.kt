/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.arch.error.presenters


class ToastErrorPresenter(
    duration: ToastDuration = ToastDuration.SHORT
) : ErrorPresenter<String>

enum class ToastDuration {
    SHORT,
    LONG
}
