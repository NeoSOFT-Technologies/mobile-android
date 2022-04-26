package com.arch.permissions

interface IPermissionsController {
    suspend fun requestPermission(permission: Permission)
    fun isPermissionGranted(permission: Permission): Boolean
}