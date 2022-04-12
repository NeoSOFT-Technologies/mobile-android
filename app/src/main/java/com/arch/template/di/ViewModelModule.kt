package com.arch.template.di


import com.arch.error.mappers.ExceptionMappers
import com.arch.error.presenters.BaseErrorPresenter
import com.arch.error.presenters.ToastDuration
import com.arch.logger.AppLogger
import com.arch.presentation.error.handler.AndroidExceptionHandlerBinder
import com.arch.presentation.error.presenter.AndroidErrorPresenter
import com.arch.template.errors.handler.AndroidExceptionHandlerBinderImpl
import com.arch.template.errors.presenters.SelectorAndroidErrorPresenter
import com.arch.template.errors.presenters.ToastAndroidErrorPresenter
import com.arch.template.utils.MyAppLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    fun provideLogger(): AppLogger = MyAppLogger


    @Provides
    fun providesToastAndroidErrorPresenter(
    ): BaseErrorPresenter<String> =
        ToastAndroidErrorPresenter(
            duration = ToastDuration.LONG
        )

    @Provides
    fun exceptionHandler(): AndroidExceptionHandlerBinder =
        AndroidExceptionHandlerBinderImpl(
            androidErrorPresenter = SelectorAndroidErrorPresenter { throwable ->
                ToastAndroidErrorPresenter(
                    duration = ToastDuration.LONG
                )
            },
            exceptionMapper = ExceptionMappers.throwableMapper(),
            onCatch = {
                MyAppLogger.d("Got exception: $it")
            }
        )

}