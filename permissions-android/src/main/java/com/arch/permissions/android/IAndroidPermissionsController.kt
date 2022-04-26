package com.arch.permissions.android

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.arch.permissions.IPermissionsController

interface IAndroidPermissionsController : IPermissionsController {

    fun bind(lifecycle: Lifecycle, fragmentManager: FragmentManager)

    companion object {
        operator fun invoke(
            resolverFragmentTag: String = "PermissionsControllerResolver",
            applicationContext: Context
        ): IPermissionsController {
            return AndroidPermissionsControllerImpl(
                resolverFragmentTag = resolverFragmentTag,
                applicationContext = applicationContext
            )
        }
    }
}