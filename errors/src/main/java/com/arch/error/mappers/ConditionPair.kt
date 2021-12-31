

package com.arch.error.mappers

data class ConditionPair(
    val condition: (Throwable) -> Boolean,
    val mapper: ThrowableMapper
)
