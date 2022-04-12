package com.arch.usecase.base

interface BaseUseCase<P : Params, T> {
    suspend fun execute(params: P): T
}
