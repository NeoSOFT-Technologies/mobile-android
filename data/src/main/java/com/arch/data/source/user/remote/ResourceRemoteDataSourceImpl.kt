package com.arch.data.source.user.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.core.entity.ResourceData
import retrofit2.Response
import javax.inject.Inject

class ResourceRemoteDataSourceImpl @Inject constructor(
    private val resourceDataPagingSource: ResourceDataPagingSource,
) : ResourceRemoteDataSource {
    override suspend fun getResourceData(): Response<Pager<Int, ResourceData>> {
        return Response.success(
            Pager(config = PagingConfig(pageSize = 4),
                pagingSourceFactory = { resourceDataPagingSource },
            )
        )
    }
}