package com.arch.mediaandroid.di

import com.arch.mediaandroid.picker.AndroidMediaPickerController
import com.arch.mediaandroid.picker.MediaPickerControllerImpl
import com.arch.permissions.android.IAndroidPermissionsController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MediaModule {

    @Provides
    fun providesMediaHelper(
        permissionsController: IAndroidPermissionsController,
    ): AndroidMediaPickerController =
        MediaPickerControllerImpl(
            permissionsController,
            "tag1",
            "tag2"
        )
}