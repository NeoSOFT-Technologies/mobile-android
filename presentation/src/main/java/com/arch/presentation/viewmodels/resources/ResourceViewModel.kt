package com.arch.presentation.viewmodels.resources


import androidx.databinding.Observable
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.arch.entity.ResourceData
import com.arch.error.BaseError
import com.arch.errors.android.handler.IAndroidExceptionHandler
import com.arch.logger.AppLogger
import com.arch.permissions.Permission
import com.arch.permissions.android.IAndroidPermissionsController
import com.arch.permissions.exceptions.PermissionDeniedAlwaysException
import com.arch.permissions.exceptions.PermissionDeniedException
import com.arch.presentation.model.ResourceDataPresentation
import com.arch.presentation.viewmodels.base.ObservableBaseViewModel
import com.arch.usecase.GetResourcesUseCase
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
    private val getResourcesUseCase: GetResourcesUseCase,
    exceptionHandler: IAndroidExceptionHandler,
    permissionHandler: IAndroidPermissionsController,
    logger: AppLogger,
) :
    ObservableBaseViewModel(exceptionHandler, permissionHandler, logger), Observable {


    private val _resourcePagingFlow: MutableSharedFlow<PagingData<ResourceDataPresentation>> =
        MutableSharedFlow()

    val resourcePagingFlow: SharedFlow<PagingData<ResourceDataPresentation>> = _resourcePagingFlow


    fun getResourceData() {
        viewModelScope.launch {
            exceptionHandler.handle {
                val resourcesParams = GetResourcesUseCase.ResourcesParams(
                    PagingConfig(pageSize = 4)
                )
                object : RequestManager<Pager<Int, ResourceData>>(params = resourcesParams) {
                    override suspend fun createCall(): Either<BaseError, Pager<Int, ResourceData>> {
                        return getResourcesUseCase.execute(
                            params = resourcesParams
                        )
                    }
                }.asFlow().collect {
                    it.data?.flow?.collect { pagingData ->
                        _resourcePagingFlow.emit(pagingData.map { resource ->
                            ResourceDataPresentation().restore(resource)
                        })
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

    fun requestForGalleryPermission() {
        viewModelScope.launch {
            exceptionHandler.handle {
                permissionHandler.requestPermission(Permission.CAMERA)
                // Permission has been granted successfully.
                logger.d("Permission Granted")
                // give callback to perform some action or future code to execute
            }.catch<Exception> {
                val handled: Boolean = when (it) {

                    // Permission is always denied.
                    is PermissionDeniedAlwaysException -> {
                        logger.d("PermissionDeniedAlwaysException, show rational settings dialog")
                        // show rational in activity by callback

                        // we tell the exceptionHandler that
                        // we are handling the error by our own & return
                        // true to tell the system to ignore
                        true
                    }

                    // Permission was denied.
                    is PermissionDeniedException -> {
                        logger.d("Permission DeniedException")
                        // show system level default error
                        false
                    }
                    else -> false // here the system is told to handle the error
                }

                handled
            }.execute()

        }
    }


}