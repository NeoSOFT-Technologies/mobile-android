package com.arch.data.di

import android.app.Application
import android.content.Context
import com.arch.data.database.AppDatabase
import com.arch.data.database.DatabaseProperties
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {


    @Provides
    fun providesRoomDatabase(application: Application): AppDatabase =
        DatabaseProperties.getInstance(application)

}