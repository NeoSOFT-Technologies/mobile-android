package com.arch.presentation.di


import com.arch.error.AppError
import com.arch.error.mappers.ExceptionMappers
import com.arch.errors.android.di.qualifier.AlertPresenter
import com.arch.errors.android.di.qualifier.SnackBarPresenter
import com.arch.errors.android.handler.AndroidExceptionHandlerImpl
import com.arch.errors.android.handler.IAndroidExceptionHandler
import com.arch.errors.android.presenter.IAndroidErrorPresenter
import com.arch.errors.android.presenter.SelectorAndroidErrorPresenter
import com.arch.logger.AppLogger
import com.arch.presentation.di.qualifier.LoginViewModelExceptionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @LoginViewModelExceptionHandler
    fun loginCustomViewModelExceptionHandler(
        appLogger: AppLogger,
        @AlertPresenter alertPresenter: IAndroidErrorPresenter<String>,
        @SnackBarPresenter snackBarPresenter: IAndroidErrorPresenter<String>
    ): IAndroidExceptionHandler =
        AndroidExceptionHandlerImpl(
            androidErrorPresenter = SelectorAndroidErrorPresenter { throwable ->
                when (throwable) {
                    is AppError -> {
                        alertPresenter
                    }
                    else -> {
                        snackBarPresenter
                    }
                }

            },
            exceptionMapper = ExceptionMappers.throwableMapper(),
            onCatch = {
                appLogger.d("Got exception: $it")
            }
        )


}