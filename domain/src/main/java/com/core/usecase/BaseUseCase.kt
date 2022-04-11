package com.core.usecase

interface BaseUseCase<T> {
    suspend fun execute(): T
}
