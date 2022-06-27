package com.arch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.arch.data.entity.remote.response.ResourceDataResponseEntity
import com.arch.data.network.utils.safeApiCall
import com.arch.data.source.resource.local.ResourceLocalDataSource
import com.arch.data.source.resource.remote.ResourceRemoteDataSource
import com.arch.data.utils.NetworkBoundResource
import com.arch.entity.ResourceData
import com.arch.error.NetworkError
import com.arch.logger.AppLogger
import com.arch.repository.ResourceRepository
import com.arch.utils.Either
import com.arch.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ResourceRepositoryImpl @Inject constructor(
    private val resourceRemoteDataSource: ResourceRemoteDataSource,
    private val resourceLocalDataSource: ResourceLocalDataSource,
    private val logger: AppLogger,
) : ResourceRepository {

    override suspend fun getResourceList(pagingConfig: PagingConfig): Either<NetworkError, Pager<Int, ResourceData>> {

        return when (val response = safeApiCall {
            resourceRemoteDataSource.getResourceList(pageConfig = pagingConfig)
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

    override suspend fun getResourceDetails(id: Int): Flow<Resource<ResourceData?>> {
        return object : NetworkBoundResource<ResourceData?, ResourceDataResponseEntity>(logger) {
            override suspend fun fetchFromLocal(): Flow<ResourceData?> {
                return flow {
                    resourceLocalDataSource.getResourceInfo(id)?.transform()
                }
            }

            override suspend fun fetchFromRemote(): Flow<Resource<ResourceDataResponseEntity>> {
                return flow {
                    emit(Resource.loading())
                    when (val response = safeApiCall {
                        resourceRemoteDataSource.getResourceDetails(
                            id
                        )
                    }) {
                        is Either.Left -> {
                            emit(
                                Resource.error(
                                    response.left.getFriendlyMessage(),
                                    null,
                                    response.left
                                )
                            )
                        }
                        is Either.Right -> {
                            response.right.body()?.let {
                                it.data?.let { data ->
                                    emit(Resource.success(data))
                                }
                            } ?: Either.Left(
                                NetworkError(
                                    message = response.right.message(),
                                    httpError = response.right.code()
                                )
                            )

                        }
                    }
                }
            }

            override suspend fun saveRemoteData(data: ResourceDataResponseEntity) {
                logger.d(data.transform().toString())
                resourceLocalDataSource.saveResourceInfo(data.transform())
            }


            override suspend fun shouldFetchFromRemote(data: ResourceData?): Boolean {
                return data == null
            }

        }.asFlow().flowOn(Dispatchers.IO)
    }

}


