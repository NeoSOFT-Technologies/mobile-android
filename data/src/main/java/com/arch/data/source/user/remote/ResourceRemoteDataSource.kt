package com.arch.data.source.user.remote

import androidx.paging.Pager
import com.core.entity.ResourceData
import retrofit2.Response

interface ResourceRemoteDataSource {
   suspend fun getResourceData(): Response<Pager<Int, ResourceData>>
}