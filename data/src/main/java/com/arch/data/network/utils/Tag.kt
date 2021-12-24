package com.arch.data.network.utils

/**
 * A annotation class to marking api requests.
 * This tag can be identified later on in the interceptors
 */
@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
annotation class Tag(val value: String)
