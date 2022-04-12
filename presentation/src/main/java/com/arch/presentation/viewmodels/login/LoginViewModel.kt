package com.arch.presentation.viewmodels.login

import androidx.lifecycle.viewModelScope
import com.arch.entity.User
import com.arch.error.BaseError
import com.arch.error.presenters.BaseErrorPresenter
import com.arch.logger.AppLogger
import com.arch.presentation.error.handler.AndroidExceptionHandlerBinder
import com.arch.presentation.viewmodels.base.BaseViewModel
import com.arch.usecase.LoginUseCase
import com.arch.utils.Either
import com.arch.utils.RequestManager
import com.arch.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    exceptionHandler: AndroidExceptionHandlerBinder,
    defaultAndroidErrorPresenter: BaseErrorPresenter<String>, logger: AppLogger,
) :
    BaseViewModel(exceptionHandler, defaultAndroidErrorPresenter, logger) {

    private val _tokenFlow: MutableStateFlow<Resource<User>> = MutableStateFlow(Resource.error(""))

    val tokenFlow: StateFlow<Resource<User>> = _tokenFlow

    fun doLogin(email: String, password: String) {
        viewModelScope.launch {
            exceptionHandler.handle {
                var authParams =  LoginUseCase.AuthParams(
                    email,
                    password,
                )
                object : RequestManager<User>(params = authParams) {
                    override suspend fun createCall(): Either<BaseError, User> {
                        return loginUseCase.execute(authParams)
                    }
                }.asFlow().collect {
                    _tokenFlow.value = it
                }
            }.catch<Exception> {
                false
            }.execute()
        }
    }

}