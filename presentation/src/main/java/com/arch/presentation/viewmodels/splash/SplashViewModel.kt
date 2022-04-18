package com.arch.presentation.viewmodels.splash


import androidx.lifecycle.viewModelScope
import com.arch.logger.AppLogger
import com.arch.presentation.error.handler.AndroidExceptionHandlerBinder
import com.arch.presentation.viewmodels.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    exceptionHandler: AndroidExceptionHandlerBinder,
    logger: AppLogger
) : BaseViewModel(
    exceptionHandler, logger,
) {

    private val _navigationFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val navigationFlow: StateFlow<Boolean> = _navigationFlow

    init {
        viewModelScope.launch {
            flow<Boolean> {
                delay(5000L)
                emit(true)
            }.collect {
                _navigationFlow.value = true
            }
        }
    }
}