package com.arch.presentation.viewmodels.base

import androidx.lifecycle.ViewModel
import com.arch.logger.AppLogger
import com.arch.presentation.error.handler.AndroidExceptionHandlerBinder


abstract class BaseViewModel(
    val exceptionHandler: AndroidExceptionHandlerBinder,
    protected val logger: AppLogger
) :
    ViewModel() {


}
