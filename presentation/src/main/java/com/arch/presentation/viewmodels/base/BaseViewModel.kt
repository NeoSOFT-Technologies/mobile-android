package com.arch.presentation.viewmodels.base

import androidx.lifecycle.ViewModel
import com.arch.errors.android.handler.IAndroidExceptionHandler
import com.arch.logger.AppLogger
import com.arch.permissions.android.IAndroidPermissionsController


abstract class BaseViewModel(
    val exceptionHandler: IAndroidExceptionHandler,
    val permissionHandler: IAndroidPermissionsController,
    val logger: AppLogger
) :
    ViewModel() {


}
