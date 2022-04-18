package com.arch.data.source.resource.remote

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arch.data.network.RetrofitAppServices
import com.arch.entity.ResourceData
import retrofit2.HttpException
import java.io.IOException

class ResourceDataPagingSource(
    private val retrofitAppServices: RetrofitAppServices,
    private val pageConfig: PagingConfig
) :
    PagingSource<Int, ResourceData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResourceData> {
        val pageIndex = params.key ?: 1
        return try {
            val response = retrofitAppServices.getResourceData(
                perPage = pageConfig.pageSize,
                page = pageIndex,
            )
            val resourceData: List<ResourceData> = response.body()?.data?.map {
                it.transform()
            } ?: emptyList()
            val nextKey =
                if (resourceData.isEmpty()) {
                    null
                } else {
                    pageIndex + 1
                }
            LoadResult.Page(
                data = resourceData,
                prevKey = if (pageIndex == 1) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResourceData>): Int? {
        return null
    }
}