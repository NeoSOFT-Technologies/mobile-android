package com.arch.template.feature.resource


import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import com.arch.template.base.BaseViewModel
import com.arch.template.util.RequestManager
import com.arch.template.utils.MyAppLogger
import com.core.entity.ResourceData
import com.core.error.BaseError
import com.core.repository.ResourceRepository
import com.core.utils.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResourceViewModel @Inject constructor(private val resourceRepository: ResourceRepository) :
    BaseViewModel() {
    private val _resourcePagingFlow: MutableSharedFlow<PagingData<ResourceData>> =
        MutableSharedFlow()

    val resourcePagingFlow: SharedFlow<PagingData<ResourceData>> = _resourcePagingFlow
    fun getResourceData() {
        viewModelScope.launch {
            exceptionHandler.handle {
                object : RequestManager<Pager<Int, ResourceData>>({
                    true
                }) {
                    override suspend fun createCall(): Either<BaseError, Pager<Int, ResourceData>> {
                        return resourceRepository.getResourceData()
                    }
                }
                    .asFlow().collect {
                        it.data?.flow?.collect {
                            _resourcePagingFlow.emit(it)
                        }
                    }
                //request
            }.catch<Exception> {
                MyAppLogger.d("Got CustomException!")
                false
            }.finally {
                MyAppLogger.d("Got CustomException finally!")
            }.execute()
        }
    }

    private var exceptionFlag = true

    fun tryError() {
        MyAppLogger.d("tryError")
        viewModelScope.launch {
            exceptionHandler.handle {
                if (exceptionFlag) {
                    exceptionFlag = !exceptionFlag
                    throw NullPointerException()
                } else {
                    exceptionFlag = !exceptionFlag
                    throw IllegalArgumentException()
                }
                //request
            }.catch<Exception> {
                MyAppLogger.d("Got CustomException!")
                false
            }.finally {
                MyAppLogger.d("Got CustomException finally!")
            }.execute()
        }
    }


}