package com.arch.data.di

import com.arch.data.repository.UserRepositoryImpl
import com.arch.data.source.user.local.UserLocalDataSource
import com.arch.data.source.user.remote.UserRemoteDataSource
import com.core.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun userRepository(
        userLocalDataSource: UserLocalDataSource,
        userRemoteDataSource: UserRemoteDataSource,
    ): UserRepository = UserRepositoryImpl (userLocalDataSource, userRemoteDataSource)
}