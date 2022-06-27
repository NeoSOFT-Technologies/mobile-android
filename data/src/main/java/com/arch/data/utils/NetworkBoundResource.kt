package com.arch.data.utils

import com.arch.logger.AppLogger
import com.arch.utils.Resource
import com.arch.utils.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*


/**
 * A super cool utility class to provide controlled data cache-ing
 */
abstract class NetworkBoundResource<DB, REMOTE>(
    private val logger: AppLogger
) {

    abstract suspend fun fetchFromLocal(): Flow<DB>

    abstract suspend fun fetchFromRemote(): Flow<Resource<REMOTE>>

    abstract suspend fun saveRemoteData(data: REMOTE)

    abstract suspend fun shouldFetchFromRemote(data: DB?): Boolean

    @ExperimentalCoroutinesApi
    fun asFlow() = flow<Resource<DB>> {

        logger.d("----------------------")
        logger.d("Starting...")
        emit(Resource.loading(null))
        val localData = fetchFromLocal().firstOrNull()
        // checking if local data is staled
        if (shouldFetchFromRemote(localData)) {

            logger.d("Fetching from remote")
            // need remote data
            fetchFromRemote()
                .collect { response ->
                    when (response.status) {

                        Status.LOADING -> {
                            logger.d("Remote is loading")
                            emit(Resource.loading(localData))
                        }

                        Status.SUCCESS -> {
                            logger.d("Remote got data")
                            val data = response.data!!
                            saveRemoteData(data)

                            // start watching it
                            emitLocalDbData()
                        }

                        Status.ERROR -> {
                            logger.d("Remote met with an error $localData")
                            emit(
                                Resource.error(
                                    response.message!!, localData
                                )
                            )
                        }
                    }
                }
        } else {
            logger.d("Fetching from local")
            // valid cache, no need to fetch from remote.
            emitLocalDbData()
        }
    }

    @ExperimentalCoroutinesApi
    private suspend fun FlowCollector<Resource<DB>>.emitLocalDbData() {
        emitAll(
            fetchFromLocal().map { dbData ->
                logger.d("Sending local...")
                Resource.success(dbData)
            }
        )
    }
}