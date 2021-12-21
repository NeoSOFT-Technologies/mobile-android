package com.arch.template.di

import com.arch.remote.service.okhttp.OkHttpClientService
import com.arch.remote.service.retrofit.RetrofitAppServices
import com.arch.remote.service.retrofit.RetrofitService
import com.arch.template.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        OkHttpClientService(httpLoggingInterceptor).okHttpClient

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        RetrofitService(okHttpClient, BuildConfig.BASE_URL).retrofit

    @Provides
    @Singleton
    fun providesAppService(retrofit: Retrofit): RetrofitAppServices =
        retrofit.create(RetrofitAppServices::class.java)

}