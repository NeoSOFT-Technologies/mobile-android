package com.arch.data.source.resource.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.arch.entity.ResourceData
import retrofit2.Response

interface ResourceRemoteDataSource {
   suspend fun getResourceData(pageConfig: PagingConfig): Response<Pager<Int, ResourceData>>
}