package com.arch.data.source.resource.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.arch.data.entity.remote.response.ResourceDataResponseEntity
import com.arch.data.entity.remote.response.ResourceDetailResponseEntity
import com.arch.entity.ResourceData
import retrofit2.Response

interface ResourceRemoteDataSource {
    suspend fun getResourceList(pageConfig: PagingConfig): Response<Pager<Int, ResourceData>>
    suspend fun getResourceDetails(id: Int): Response<ResourceDetailResponseEntity>
}