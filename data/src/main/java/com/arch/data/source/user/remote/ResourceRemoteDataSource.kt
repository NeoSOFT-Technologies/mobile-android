package com.arch.data.source.user.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.core.entity.ResourceData
import retrofit2.Response

interface ResourceRemoteDataSource {
   suspend fun getResourceData(pageConfig: PagingConfig): Response<Pager<Int, ResourceData>>
}