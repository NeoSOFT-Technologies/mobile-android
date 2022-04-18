package com.arch.template.di


import com.arch.error.AppError
import com.arch.error.mappers.ExceptionMappers
import com.arch.error.presenters.SnackBarDuration
import com.arch.error.presenters.ToastDuration
import com.arch.logger.AppLogger
import com.arch.presentation.error.handler.AndroidExceptionHandlerBinder
import com.arch.presentation.error.handler.AndroidExceptionHandlerBinderImpl
import com.arch.presentation.error.presenter.AndroidErrorPresenter
import com.arch.presentation.qualifier.error.AlertPresenter
import com.arch.presentation.qualifier.error.SelectorAndroidPresenter
import com.arch.presentation.qualifier.error.SnackBarPresenter
import com.arch.presentation.qualifier.error.ToastPresenter
import com.arch.presentation.qualifier.handler.LoginViewModelExceptionHandler
import com.arch.template.ui.errors.presenters.AlertAndroidErrorPresenter
import com.arch.template.ui.errors.presenters.SelectorAndroidErrorPresenter
import com.arch.template.ui.errors.presenters.SnackBarAndroidErrorPresenter
import com.arch.template.ui.errors.presenters.ToastAndroidErrorPresenter
import com.arch.template.utils.MyAppLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    fun provideLogger(): AppLogger = MyAppLogger


    @Provides
    @ToastPresenter
    fun providesToastAndroidErrorPresenter(
    ): AndroidErrorPresenter<String> =
        ToastAndroidErrorPresenter(
            duration = ToastDuration.LONG
        )

    @Provides
    @SnackBarPresenter
    fun providesSnackBarAndroidErrorPresenter(
    ): AndroidErrorPresenter<String> =
        SnackBarAndroidErrorPresenter(
            SnackBarDuration.SHORT
        )

    @Provides
    @AlertPresenter
    fun providesAlertAndroidErrorPresenter(
    ): AndroidErrorPresenter<String> =
        AlertAndroidErrorPresenter(

        )

    @Provides
    @SelectorAndroidPresenter
    fun providesSelectorAndroidErrorPresenter(
    ): AndroidErrorPresenter<String> =
        SelectorAndroidErrorPresenter {
            ToastAndroidErrorPresenter(
                duration = ToastDuration.LONG
            )
        }

    @Provides
    fun defaultExceptionHandler(@SelectorAndroidPresenter androidErrorPresenter: AndroidErrorPresenter<String>): AndroidExceptionHandlerBinder =
        AndroidExceptionHandlerBinderImpl(
            androidErrorPresenter = androidErrorPresenter,
            exceptionMapper = ExceptionMappers.throwableMapper(),
            onCatch = {
                MyAppLogger.d("Got exception: $it")
            }
        )

    @Provides
    @LoginViewModelExceptionHandler
    fun someCustomViewModelExceptionHandler(
        @AlertPresenter alertPresenter: AndroidErrorPresenter<String>,
        @SnackBarPresenter snackBarPresenter: AndroidErrorPresenter<String>
    ): AndroidExceptionHandlerBinder =
        AndroidExceptionHandlerBinderImpl(
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
                MyAppLogger.d("Got exception: $it")
            }
        )

}