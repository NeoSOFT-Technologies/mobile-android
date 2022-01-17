package com.arch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.arch.data.network.utils.safeApiCall
import com.arch.data.source.resource.remote.ResourceRemoteDataSource
import com.core.entity.ResourceData
import com.core.error.NetworkError
import com.core.repository.ResourceRepository
import com.core.utils.Either
import javax.inject.Inject

class ResourceRepositoryImpl @Inject constructor(
    private val resourceRemoteDataSource: ResourceRemoteDataSource,
) : ResourceRepository {

    override suspend fun getResourceData(pagingConfig: PagingConfig): Either<NetworkError, Pager<Int, ResourceData>> {
        return when (val response = safeApiCall {
            resourceRemoteDataSource.getResourceData(pageConfig = pagingConfig)
        }) {
            is Either.Left -> Either.Left(
                response.left
            )
            is Either.Right -> {
                response.right.body()?.let {
                    Either.Right(it)
                } ?: kotlin.run {
                    Either.Left(
                        NetworkError(
                            message = response.right.message(),
                            httpError = response.right.code()
                        )
                    )
                }
            }
        }
    }
}