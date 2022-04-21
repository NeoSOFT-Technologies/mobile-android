package com.arch.permissions.exceptions

import com.arch.permissions.Permission

class PermissionRequestCanceledException(
    val permission: Permission,
    message: String? = null
) : Exception(message)