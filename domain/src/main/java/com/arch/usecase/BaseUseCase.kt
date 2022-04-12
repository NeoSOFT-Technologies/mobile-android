package com.arch.usecase

interface BaseUseCase<T> {
    suspend fun execute(): T
}
