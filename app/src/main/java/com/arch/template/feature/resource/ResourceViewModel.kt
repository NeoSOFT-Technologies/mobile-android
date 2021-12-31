package com.arch.template.feature.resource

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import com.arch.error.handler.ExceptionHandler
import com.arch.error.mappers.ExceptionMappersStorage
import com.arch.error.presenters.SnackBarDuration
import com.arch.error.presenters.ToastDuration
import com.arch.error.presenters.ToastErrorPresenter
import com.arch.template.base.BaseViewModel
import com.arch.template.util.RequestManager
import com.core.entity.ResourceData
import com.core.error.BaseError
import com.core.repository.ResourceRepository
import com.core.utils.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.icerock.moko.errors.presenters.SnackBarErrorPresenter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ResourceViewModel @Inject constructor(private val resourceRepository: ResourceRepository) :
    BaseViewModel() {
    val exceptionHandler: ExceptionHandler

    val snackBarErrorPresenter = SnackBarErrorPresenter(
        duration = SnackBarDuration.SHORT
    )
    val toastErrorPresenter = ToastErrorPresenter(
        duration = ToastDuration.LONG
    )

    init {
        exceptionHandler = ExceptionHandler(
            errorPresenter = toastErrorPresenter,
            exceptionMapper = ExceptionMappersStorage.throwableMapper(),
            onCatch = {
                // E.g. here we can log all exceptions that are handled by ExceptionHandler
                println("Got exception: $it")
            }
        )
    }

    private val _resourcePagingFlow: MutableSharedFlow<PagingData<ResourceData>> =
        MutableSharedFlow()

    val resourcePagingFlow: SharedFlow<PagingData<ResourceData>> = _resourcePagingFlow
    fun getResourceData() {
        viewModelScope.launch {
            object : RequestManager<Pager<Int, ResourceData>>() {
                override suspend fun createCall(): Either<BaseError, Pager<Int, ResourceData>> {
                    return resourceRepository.getResourceData()
                }
            }
                .asFlow().collect {
                    it.data?.flow?.collect {
                        _resourcePagingFlow.emit(it)
                    }
                }
        }
    }

    private var exceptionFlag = false

    fun tryError() {
        viewModelScope.launch {
            exceptionHandler.handle {
                if (exceptionFlag) {
                    exceptionFlag = !exceptionFlag
                    throw IllegalArgumentException()
                } else {
                    exceptionFlag = !exceptionFlag
                    throw IllegalArgumentException()
                }
            }.catch<Exception> {
                println("Got CustomException!")
                false
            }.finally {
                println("Got CustomException finally!")
            }.execute()
        }
    }
}