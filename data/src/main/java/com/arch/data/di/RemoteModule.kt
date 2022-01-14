package com.arch.data.di


import com.arch.data.BuildConfig
import com.arch.data.network.RetrofitAppServices
import com.arch.data.network.RetrofitService
import com.arch.data.network.okhttp.OkHttpClientService
import com.arch.data.source.resource.remote.ResourceRemoteDataSource
import com.arch.data.source.resource.remote.ResourceRemoteDataSourceImpl
import com.arch.data.source.user.remote.UserRemoteDataSource
import com.arch.data.source.user.remote.UserRemoteDataSourceImpl
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

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        retrofitAppServices: RetrofitAppServices,
    ): UserRemoteDataSource =
        UserRemoteDataSourceImpl(retrofitAppServices)

    @Provides
    @Singleton
    fun providerResourceRemoteDataSource(retrofitAppServices: RetrofitAppServices): ResourceRemoteDataSource =
        ResourceRemoteDataSourceImpl(retrofitAppServices)
}