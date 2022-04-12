package com.arch.data.source.resource.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.arch.data.network.RetrofitAppServices
import com.arch.entity.ResourceData
import retrofit2.Response
import javax.inject.Inject

class ResourceRemoteDataSourceImpl @Inject constructor(private val retrofitAppServices: RetrofitAppServices) :
    ResourceRemoteDataSource {
    override suspend fun getResourceData(pageConfig: PagingConfig): Response<Pager<Int, ResourceData>> {
        return Response.success(
            Pager(
                config = pageConfig,
                pagingSourceFactory = {
                    ResourceDataPagingSource(
                        retrofitAppServices = retrofitAppServices,
                        pageConfig
                    )
                },
            )
        )
    }
}