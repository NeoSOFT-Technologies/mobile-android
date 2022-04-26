package com.arch.permissions.android

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.arch.permissions.Permission
import kotlin.coroutines.suspendCoroutine

class AndroidPermissionsControllerImpl(
    private val resolverFragmentTag: String = "PermissionsControllerResolver",
    private val applicationContext: Context
) : IAndroidPermissionsController {
    var fragmentManager: FragmentManager? = null

    override fun bind(lifecycle: Lifecycle, fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager

        val observer = object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroyed(source: LifecycleOwner) {
                this@AndroidPermissionsControllerImpl.fragmentManager = null
                source.lifecycle.removeObserver(this)
            }
        }
        lifecycle.addObserver(observer)
    }

    override suspend fun requestPermission(permission: Permission) {
        val fragmentManager =
            fragmentManager
                ?: throw IllegalStateException("can't resolve permission without active window")

        val currentFragment: Fragment? = fragmentManager.findFragmentByTag(resolverFragmentTag)
        val resolverFragment: ResolverFragment = if (currentFragment != null) {
            currentFragment as ResolverFragment
        } else {
            ResolverFragment().apply {
                fragmentManager
                    .beginTransaction()
                    .add(this, resolverFragmentTag)
                    .commitNow()
            }
        }

        val platformPermission = permission.toPlatformPermission()
        suspendCoroutine<Unit> { continuation ->
            resolverFragment.requestPermission(
                permission,
                platformPermission
            ) { continuation.resumeWith(it) }
        }
    }

    override fun isPermissionGranted(permission: Permission): Boolean {
        if (permission == Permission.REMOTE_NOTIFICATION && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return NotificationManagerCompat.from(applicationContext).areNotificationsEnabled()
        }
        return permission.toPlatformPermission().all {
            val status = ContextCompat.checkSelfPermission(applicationContext, it)
            status == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun Permission.toPlatformPermission(): List<String> {
        return when (this) {
            Permission.CAMERA -> listOf(Manifest.permission.CAMERA)
            Permission.GALLERY -> listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            Permission.STORAGE -> listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            Permission.WRITE_STORAGE -> listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            Permission.LOCATION -> listOf(Manifest.permission.ACCESS_FINE_LOCATION)
            Permission.COARSE_LOCATION -> listOf(Manifest.permission.ACCESS_COARSE_LOCATION)
            Permission.BLUETOOTH_LE -> listOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            Permission.REMOTE_NOTIFICATION -> emptyList()
            Permission.RECORD_AUDIO -> listOf(Manifest.permission.RECORD_AUDIO)
        }
    }
}