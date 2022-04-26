package com.arch.errors.android.di

import com.arch.error.mappers.ExceptionMappers
import com.arch.error.presenters.SnackBarDuration
import com.arch.error.presenters.ToastDuration
import com.arch.errors.android.di.qualifier.AlertPresenter
import com.arch.errors.android.di.qualifier.SelectorAndroidPresenter
import com.arch.errors.android.di.qualifier.SnackBarPresenter
import com.arch.errors.android.di.qualifier.ToastPresenter
import com.arch.errors.android.handler.AndroidExceptionHandlerImpl
import com.arch.errors.android.handler.IAndroidExceptionHandler
import com.arch.errors.android.presenter.*
import com.arch.logger.AppLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ErrorsModule {

    @Provides
    @ToastPresenter
    fun providesToastAndroidErrorPresenter(
    ): IAndroidErrorPresenter<String> =
        ToastAndroidErrorPresenter(
            duration = ToastDuration.LONG
        )

    @Provides
    @SnackBarPresenter
    fun providesSnackBarAndroidErrorPresenter(
    ): IAndroidErrorPresenter<String> =
        SnackBarAndroidErrorPresenter(
            SnackBarDuration.SHORT
        )

    @Provides
    @AlertPresenter
    fun providesAlertAndroidErrorPresenter(
    ): IAndroidErrorPresenter<String> =
        AlertAndroidErrorPresenter()

    @Provides
    @SelectorAndroidPresenter
    fun providesSelectorAndroidErrorPresenter(
    ): IAndroidErrorPresenter<String> =
        SelectorAndroidErrorPresenter {
            ToastAndroidErrorPresenter(
                duration = ToastDuration.LONG
            )
        }

    @Provides
    fun defaultExceptionHandler(
        @SelectorAndroidPresenter androidErrorPresenter: IAndroidErrorPresenter<String>,
        appLogger: AppLogger
    ): IAndroidExceptionHandler =
        AndroidExceptionHandlerImpl(
            androidErrorPresenter = androidErrorPresenter,
            exceptionMapper = ExceptionMappers.throwableMapper(),
            onCatch = {
                appLogger.d("Got exception: $it")
            }
        )
}