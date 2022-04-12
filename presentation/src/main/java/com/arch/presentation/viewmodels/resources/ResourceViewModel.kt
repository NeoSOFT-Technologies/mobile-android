package com.arch.presentation.viewmodels.resources


import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arch.error.handler.BaseExceptionHandler
import com.arch.error.presenters.BaseErrorPresenter
import com.arch.logger.AppLogger
import com.arch.presentation.viewmodels.base.BaseViewModel

import com.arch.entity.ResourceData
import com.arch.error.BaseError
import com.arch.presentation.error.handler.AndroidExceptionHandlerBinder
import com.arch.repository.ResourceRepository
import com.arch.utils.Either
import com.arch.utils.RequestManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResourceViewModel @Inject constructor(
    private val resourceRepository: ResourceRepository,
    exceptionHandler: AndroidExceptionHandlerBinder,
    defaultAndroidErrorPresenter: BaseErrorPresenter<String>, logger: AppLogger,
) :
    BaseViewModel(exceptionHandler, defaultAndroidErrorPresenter, logger) {
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
                        return resourceRepository.getResourceData(PagingConfig(pageSize = 4))
                    }
                }
                    .asFlow().collect {
                        it.data?.flow?.collect {
                            _resourcePagingFlow.emit(it)
                        }
                    }
                //request
            }.catch<Exception> {
                logger.d("Got CustomException!")
                false
            }.finally {
                logger.d("Got CustomException finally!")
            }.execute()
        }
    }

    private var exceptionFlag = true

    fun tryError() {
        logger.d("tryError")
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
                logger.d("Got CustomException!")
                false
            }.finally {
                logger.d("Got CustomException finally!")
            }.execute()
        }
    }


}