package com.arch.paging


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlin.coroutines.CoroutineContext

class Pagination<Item>(
    parentScope: CoroutineScope,
    private val dataSource: PagedListDataSource<Item>,
    private val comparator: Comparator<Item>,
    private val nextPageListener: (Result<List<Item>>) -> Unit,
    private val refreshListener: (Result<List<Item>>) -> Unit,
    initValue: List<Item>? = null
) : CoroutineScope {

    override val coroutineContext: CoroutineContext = parentScope.coroutineContext

    private val mStateStorage =
        MutableLiveData<ResourceState<List<Item>, Throwable>>(initValue.asStateNullIsLoading())

    val state: LiveData<ResourceState<List<Item>, Throwable>> = mStateStorage

    private val mNextPageLoading = MutableLiveData(false)
    val nextPageLoading: LiveData<Boolean> = mNextPageLoading

    private val mEndOfList = MutableLiveData(false)

    private val mRefreshLoading = MutableLiveData(false)
    val refreshLoading: LiveData<Boolean> = mRefreshLoading

    private val listMutex = Mutex()

    private var loadNextPageDeferred: Deferred<List<Item>>? = null

    fun loadFirstPage() {
        launch {
            loadFirstPageSuspend()
        }
    }

    suspend fun loadFirstPageSuspend() {
        loadNextPageDeferred?.cancel()

        listMutex.lock()

        mEndOfList.value = false
        mNextPageLoading.value = false
        mStateStorage.value = ResourceState.Loading()

        @Suppress("TooGenericExceptionCaught")
        try {
            val items: List<Item> = dataSource.loadPage(null)
            mStateStorage.value = items.asState()
        } catch (error: Exception) {
            mStateStorage.value = ResourceState.Failed(error)
        }
        listMutex.unlock()
    }

    fun loadNextPage() {
        launch {
            loadNextPageSuspend()
        }
    }

    @Suppress("ReturnCount")
    suspend fun loadNextPageSuspend() {
        mNextPageLoading.value?.let {
            if (it) return
        }
        mRefreshLoading.value?.let {
            if (it) return
        }
        mEndOfList.value?.let {
            if (it) return
        }

        listMutex.lock()

        mNextPageLoading.value = true

        @Suppress("TooGenericExceptionCaught")
        try {
            loadNextPageDeferred = this.async {
                val currentList = mStateStorage.value?.dataValue()
                    ?: throw IllegalStateException("Try to load next page when list is empty")
                // load next page items
                val items = dataSource.loadPage(currentList)
                // remove already exist items
                val newItems = items.filter { item ->
                    val existsItem = currentList.firstOrNull { comparator.compare(item, it) == 0 }
                    existsItem == null
                }
                // append new items to current list
                val newList = currentList.plus(newItems)
                // mark end of list if no new items
                if (newItems.isEmpty()) {
                    mEndOfList.value = true
                } else {
                    // save
                    mStateStorage.value = newList.asState()
                }
                newList
            }
            val newList = loadNextPageDeferred!!.await()

            // flag
            mNextPageLoading.value = false
            // notify
            nextPageListener(Result.success(newList))
        } catch (error: Exception) {
            // flag
            mNextPageLoading.value = false
            // notify
            nextPageListener(Result.failure(error))
        }
        listMutex.unlock()
    }

    fun refresh() {
        launch {
            refreshSuspend()
        }
    }

    suspend fun refreshSuspend() {
        loadNextPageDeferred?.cancel()
        listMutex.lock()

        mRefreshLoading.value?.let {
            if (it) {
                listMutex.unlock()
                return
            }
        }

        mNextPageLoading.value?.let {
            if (it) {
                listMutex.unlock()
                return
            }
        }


        mRefreshLoading.value = true

        @Suppress("TooGenericExceptionCaught")
        try {
            // load first page items
            val items = dataSource.loadPage(null)
            // save
            mStateStorage.value = items.asState()
            // flag
            mEndOfList.value = false
            mRefreshLoading.value = false
            // notify
            refreshListener(Result.success(items))
        } catch (error: Exception) {
            mStateStorage.value = ResourceState.Failed(error)
            // flag
            mRefreshLoading.value = false
            // notify
            refreshListener(Result.failure(error))
        }
        listMutex.unlock()
    }

    fun setData(items: List<Item>?) {
        launch {
            setDataSuspend(items)
        }
    }

    suspend fun setDataSuspend(items: List<Item>?) {
        listMutex.lock()
        mStateStorage.value = items.asStateNullIsEmpty()
        mEndOfList.value = false
        listMutex.unlock()
    }
}

fun <T, E> List<T>?.asStateNullIsEmpty() = asState {
    ResourceState.Empty<List<T>, E>()
}

fun <T, E> List<T>?.asStateNullIsLoading() = asState {
    ResourceState.Loading<List<T>, E>()
}

interface IdEntity {
    val id: Long
}

class IdComparator<T : IdEntity> : Comparator<T> {
    override fun compare(a: T, b: T): Int {
        return if (a.id == b.id) 0 else 1
    }
}