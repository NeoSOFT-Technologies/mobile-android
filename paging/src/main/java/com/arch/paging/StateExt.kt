package com.arch.paging


fun <T, E> T.asState(): ResourceState<T, E> =
    ResourceState.Success(this)

fun <T, E> T?.asState(whenNull: () -> ResourceState<T, E>): ResourceState<T, E> =
    this?.asState() ?: whenNull()

fun <T, E> List<T>.asState(): ResourceState<List<T>, E> = if (this.isEmpty()) {
    ResourceState.Empty()
} else {
    ResourceState.Success(this)
}

fun <T, E> List<T>?.asState(whenNull: () -> ResourceState<List<T>, E>): ResourceState<List<T>, E> =
    this?.asState() ?: whenNull()

inline fun <reified T, reified E> ResourceState<T, E>?.nullAsEmpty(): ResourceState<T, E> =
    this ?: ResourceState.Empty()

inline fun <reified T, reified E> ResourceState<T, E>?.nullAsLoading(): ResourceState<T, E> =
    this ?: ResourceState.Loading()