package com.arch.di

import com.arch.repository.ResourceRepository
import com.arch.repository.UserRepository
import com.arch.usecase.GetResourcesUseCase
import com.arch.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providesLoginUseCase(userRepository: UserRepository): LoginUseCase =
        LoginUseCase(userRepository)

    @Provides
    fun providesGetResourcesUseCase(resourceRepository: ResourceRepository): GetResourcesUseCase =
        GetResourcesUseCase(resourceRepository)

}