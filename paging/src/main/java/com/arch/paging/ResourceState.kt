package com.arch.paging

sealed class ResourceState<out T, out E> {
    data class Success<out T, out E>(val data: T) : ResourceState<T, E>()
    data class Failed<out T, out E>(val error: E) : ResourceState<T, E>()
    class Loading<out T, out E> : ResourceState<T, E>()
    class Empty<out T, out E> : ResourceState<T, E>()

    fun isLoading(): Boolean = this is Loading
    fun isSuccess(): Boolean = this is Success
    fun isEmpty(): Boolean = this is Empty
    fun isFailed(): Boolean = this is Failed

    fun dataValue(): T? = (this as? Success)?.data

    fun errorValue(): E? = (this as? Failed)?.error
}