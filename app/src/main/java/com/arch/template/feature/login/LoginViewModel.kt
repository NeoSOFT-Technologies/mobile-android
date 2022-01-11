package com.arch.template.feature.login

import androidx.lifecycle.viewModelScope
import com.arch.template.base.BaseViewModel
import com.arch.template.errors.presenters.AndroidErrorPresenter
import com.arch.template.util.RequestManager
import com.core.entity.User
import com.core.error.AppError
import com.core.error.AppErrorType
import com.core.error.BaseError
import com.core.repository.UserRepository
import com.core.utils.Either
import com.core.utils.Resource
import com.core.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

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

    override fun getSelectorPresenter(throwable: Throwable): AndroidErrorPresenter<String> {
        return when (throwable) {
            is AppError -> {
                alertErrorPresenter
            }
            else -> {
                snackBarErrorPresenter
            }
        }
    }
}