package com.arch.template.di


import com.arch.logger.AppLogger
import com.arch.template.utils.MyAppLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModules {

    @Provides
    fun provideLogger(): AppLogger = MyAppLogger

}