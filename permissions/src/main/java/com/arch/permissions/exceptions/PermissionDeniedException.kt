package com.arch.permissions.exceptions

import com.arch.permissions.Permission

open class PermissionDeniedException(
    val permission: Permission,
    message: String? = null
) : Exception(message)

class PermissionDeniedAlwaysException(
    permission: Permission,
    message: String? = null
) : PermissionDeniedException(permission, message)