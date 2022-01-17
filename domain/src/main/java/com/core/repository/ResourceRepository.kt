package com.core.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.core.entity.ResourceData
import com.core.error.NetworkError
import com.core.utils.Either

interface ResourceRepository {
    suspend fun getResourceData(pagingConfig: PagingConfig): Either<NetworkError, Pager<Int, ResourceData>>
}