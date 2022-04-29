package com.arch.paging

class LambdaPagedListDataSource<T>(
    private val loadPageLambda: suspend (List<T>?) -> List<T>
) : PagedListDataSource<T> {
    override suspend fun loadPage(currentList: List<T>?): List<T> {
        return loadPageLambda(currentList)
    }
}