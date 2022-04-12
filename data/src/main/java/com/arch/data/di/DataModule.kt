package com.arch.data.di

import com.arch.data.repository.ResourceRepositoryImpl
import com.arch.data.repository.UserRepositoryImpl
import com.arch.data.source.resource.remote.ResourceRemoteDataSource
import com.arch.data.source.user.local.UserLocalDataSource
import com.arch.data.source.user.remote.UserRemoteDataSource
import com.arch.repository.ResourceRepository
import com.arch.repository.UserRepository
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
    ): UserRepository = UserRepositoryImpl(userLocalDataSource, userRemoteDataSource)

    @Singleton
    @Provides
    fun resourceRepository(
        resourceRemoteDataSource: ResourceRemoteDataSource,
    ): ResourceRepository =
        ResourceRepositoryImpl(resourceRemoteDataSource)
}