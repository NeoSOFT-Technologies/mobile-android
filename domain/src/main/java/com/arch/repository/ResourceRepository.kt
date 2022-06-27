package com.arch.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.arch.entity.ResourceData
import com.arch.error.NetworkError
import com.arch.utils.Either
import com.arch.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ResourceRepository {

    suspend fun getResourceList(pagingConfig: PagingConfig): Either<NetworkError, Pager<Int, ResourceData>>

    suspend fun getResourceDetails(id: Int): Flow<Resource<ResourceData?>>
}