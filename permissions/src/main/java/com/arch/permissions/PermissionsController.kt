package com.arch.permissions

interface PermissionsController {
    suspend fun requestPermission(permission: Permission)
    fun isPermissionGranted(permission: Permission): Boolean
}