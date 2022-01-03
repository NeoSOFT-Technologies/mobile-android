package com.arch.template.util

import androidx.annotation.MainThread
import com.core.error.BaseError
import com.core.utils.Either
import com.core.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class RequestManager<ResultType>() {
    private var result: Flow<Resource<ResultType>>

    init {
        result = flow {
            emit(Resource.loading(null))
            when (val response: Either<BaseError, ResultType> = createCall()) {
                is Either.Left -> {
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