package com.arch.presentation.viewmodels.resourcedetails


import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arch.errors.android.handler.IAndroidExceptionHandler
import com.arch.logger.AppLogger
import com.arch.permissions.android.IAndroidPermissionsController
import com.arch.presentation.model.ResourceDataPresentation
import com.arch.presentation.viewmodels.base.ObservableBaseViewModel
import com.arch.usecase.GetResourceDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResourceDetailsViewModel @Inject constructor(
    private val getResourceDetailsUseCase: GetResourceDetailsUseCase,
    exceptionHandler: IAndroidExceptionHandler,
    permissionHandler: IAndroidPermissionsController,
    logger: AppLogger,
) :
    ObservableBaseViewModel(exceptionHandler, permissionHandler, logger), Observable {

    private val _textName: MutableLiveData<String> = MutableLiveData("no data")
    val textName: LiveData<String> = _textName

    private val _textYear: MutableLiveData<String> = MutableLiveData("no data")
    val textYear: LiveData<String> = _textYear

    private val _textOther: MutableLiveData<String> = MutableLiveData("no data")
    val textOther: LiveData<String> = _textOther

    fun getResourceDetails(id: Int = 1) {
        logger.d("resource id $id")
        viewModelScope.launch {

            exceptionHandler.handle {
                val resourcesParams = GetResourceDetailsUseCase.ResourceParams(
                    id
                )
                //request
                getResourceDetailsUseCase.execute(
                    params = resourcesParams
                ).collect {
                    logger.d("presentation collect ${it.data}")
                    it.data?.let {
                        logger.d("presentation collect not null")
                        val presentation = ResourceDataPresentation().restore(it)
                        logger.d("presentation data ${presentation.toString()}")
                        _textName.value = (presentation.name)
                    }
                }

            }.catch<Exception> {
                logger.d("Got CustomException!")
                false
            }.finally {
                logger.d("Got CustomException finally!")
            }.execute()
        }
    }


}