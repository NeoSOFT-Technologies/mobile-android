package com.arch.permissions.android.di

import android.content.Context
import com.arch.permissions.android.AndroidPermissionsControllerImpl
import com.arch.permissions.android.IAndroidPermissionsController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PermissionsModule {

    @Provides
    fun providesToastAndroidErrorPresenter(
        @ApplicationContext applicationContext: Context
    ): IAndroidPermissionsController =
        AndroidPermissionsControllerImpl(
            applicationContext = applicationContext
        )
}