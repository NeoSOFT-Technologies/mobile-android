package com.arch.paging

interface PagedListDataSource<T> {
    suspend fun loadPage(currentList: List<T>?): List<T>
}