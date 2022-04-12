package com.arch.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.arch.entity.ResourceData
import com.arch.error.NetworkError
import com.arch.utils.Either

interface ResourceRepository {
    suspend fun getResourceData(pagingConfig: PagingConfig): Either<NetworkError, Pager<Int, ResourceData>>
}