package com.arch.presentation.viewmodels.login

import androidx.lifecycle.viewModelScope
import com.arch.entity.User
import com.arch.error.BaseError
import com.arch.error.presenters.BaseErrorPresenter
import com.arch.logger.AppLogger
import com.arch.presentation.error.handler.AndroidExceptionHandlerBinder
import com.arch.presentation.model.UserModel
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

    private val _tokenFlow: MutableStateFlow<Resource<UserModel>> =
        MutableStateFlow(Resource.error(""))

    val tokenFlow: StateFlow<Resource<UserModel>> = _tokenFlow

    fun doLogin(email: String, password: String) {
        viewModelScope.launch {
            exceptionHandler.handle {
                val authParams = LoginUseCase.AuthParams(
                    email,
                    password,
                )
                object : RequestManager<User>(params = authParams) {
                    override suspend fun createCall(): Either<BaseError, User> {
                        return loginUseCase.execute(authParams)
                    }
                }.asFlow().collect {
                    _tokenFlow.value = Resource(
                        status = it.status,
                        data = if (it.data != null) UserModel("").restore(it.data!!) else null,
                        error = it.error,
                        message = it.message,
                    )
                }
            }.catch<Exception> {
                false
            }.execute()
        }
    }

}