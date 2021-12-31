/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.arch.error.mappers

data class ConditionPair(
    val condition: (Throwable) -> Boolean,
    val mapper: ThrowableMapper
)
