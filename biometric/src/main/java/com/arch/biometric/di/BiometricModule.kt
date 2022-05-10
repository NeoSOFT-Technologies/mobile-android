package com.arch.biometric.di


import com.arch.biometric.AndroidBiometryAuthenticator
import com.arch.biometric.IAndroidBiometryAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class BiometricModule {

    @Provides
    fun providesBiometricController(
    ): IAndroidBiometryAuthenticator =
        AndroidBiometryAuthenticator()
}