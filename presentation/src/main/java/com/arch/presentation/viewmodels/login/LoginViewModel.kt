package com.arch.presentation.viewmodels.login

import androidx.lifecycle.viewModelScope
import com.arch.entity.User
import com.arch.error.AppError
import com.arch.error.AppErrorType
import com.arch.error.BaseError
import com.arch.error.presenters.BaseErrorPresenter
import com.arch.logger.AppLogger
import com.arch.presentation.error.handler.AndroidExceptionHandlerBinder
import com.arch.presentation.viewmodels.base.BaseViewModel
import com.arch.repository.UserRepository
import com.arch.utils.Either
import com.arch.utils.RequestManager
import com.arch.utils.Resource
import com.arch.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    exceptionHandler: AndroidExceptionHandlerBinder,
    defaultAndroidErrorPresenter: BaseErrorPresenter<String>, logger: AppLogger,
) :
    BaseViewModel(exceptionHandler, defaultAndroidErrorPresenter, logger) {

    private val _tokenFlow: MutableStateFlow<Resource<User>> = MutableStateFlow(Resource.error(""))

    val tokenFlow: StateFlow<Resource<User>> = _tokenFlow

    fun doLogin(email: String, password: String) {
        viewModelScope.launch {
            exceptionHandler.handle {
                object : RequestManager<User>(preCheck = {
                    when {
                        Validator.isEmpty(email) -> throw AppError(
                            appErrorType = AppErrorType.EmailEmpty,
                        )
                        !Validator.isValidEmail(email) -> throw AppError(
                            appErrorType = AppErrorType.InvalidEmail,
                        )
                        Validator.isEmpty(password) -> throw AppError(
                            appErrorType = AppErrorType.PasswordEmpty,
                        )
                    }
                }) {
                    override suspend fun createCall(): Either<BaseError, User> {
                        return userRepository.login(email, password)
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