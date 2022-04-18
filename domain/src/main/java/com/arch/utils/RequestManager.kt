package com.arch.utils

import androidx.annotation.MainThread
import com.arch.error.BaseError
import com.arch.usecase.base.Params
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class RequestManager<ResultType>(
    var params: Params
) {
    private var result: Flow<Resource<ResultType>>

    init {
        result = flow {
            emit(Resource.loading(null))
            if (params.verify()) {
                when (val response: Either<BaseError, ResultType> = createCall()) {
                    is Either.Left -> {
                        throw response.left
                    }
                    is Either.Right -> {
                        emit(Resource.success(response.right))
                    }
                }
            }
        }.catch { throwable ->
            emit(Resource.error("${throwable.message}", appError = throwable as BaseError))
            delay(1L)
            throw throwable
        }
            .flowOn(Dispatchers.IO)
    }

    @MainThread
    protected abstract suspend fun createCall(): Either<BaseError, ResultType>

    fun asFlow() = result
}
