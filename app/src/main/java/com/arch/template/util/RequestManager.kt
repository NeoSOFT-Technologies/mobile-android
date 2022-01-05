package com.arch.template.util

import androidx.annotation.MainThread
import com.core.error.AppError
import com.core.error.BaseError
import com.core.utils.Either
import com.core.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class RequestManager<ResultType>(
    onError: suspend (Resource<ResultType>) -> Boolean,
    preCheck: () -> Unit
) {
    private var result: Flow<Resource<ResultType>>

    init {
        result = flow {
            emit(Resource.loading(null))
            try {
                preCheck()
            } catch (
                e: AppError
            ) {

            }
            when (val response: Either<BaseError, ResultType> = createCall()) {
                is Either.Left -> {
                    onError?.invoke(Resource.error("", appError = response.left))
                    throw response.left
                }
                is Either.Right -> {
                    emit(Resource.success(response.right))
                }
            }
        }
            .flowOn(Dispatchers.IO)
    }

    @MainThread
    protected abstract suspend fun createCall(): Either<BaseError, ResultType>

    fun asFlow() = result
}