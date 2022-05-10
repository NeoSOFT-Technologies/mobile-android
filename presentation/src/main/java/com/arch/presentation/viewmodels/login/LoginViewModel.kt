package com.arch.presentation.viewmodels.login

import androidx.lifecycle.viewModelScope
import com.arch.biometric.IAndroidBiometryAuthenticator
import com.arch.entity.User
import com.arch.error.BaseError
import com.arch.errors.android.handler.IAndroidExceptionHandler
import com.arch.logger.AppLogger
import com.arch.permissions.android.IAndroidPermissionsController
import com.arch.presentation.di.qualifier.LoginViewModelExceptionHandler
import com.arch.presentation.model.UserPresentation
import com.arch.presentation.viewmodels.base.BaseViewModel
import com.arch.usecase.LoginUseCase
import com.arch.utils.Either
import com.arch.utils.RequestManager
import com.arch.utils.Resource
import com.arch.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    @LoginViewModelExceptionHandler exceptionHandler: IAndroidExceptionHandler,
    permissionHandler: IAndroidPermissionsController,
    logger: AppLogger,
    val biometryAuthenticator: IAndroidBiometryAuthenticator
) :
    BaseViewModel(exceptionHandler, permissionHandler, logger) {

    private val _tokenFlow: MutableStateFlow<Resource<UserPresentation>> =
        MutableStateFlow(Resource.error(""))

    val tokenFlow: StateFlow<Resource<UserPresentation>> = _tokenFlow

    private val _bioLogin: MutableStateFlow<Resource<Boolean>> =
        MutableStateFlow(Resource.error(""))

    val bioLogin: StateFlow<Resource<Boolean>> = _bioLogin

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
                        data = if (it.data != null) UserPresentation("").restore(it.data!!) else null,
                        error = it.error,
                        message = it.message,
                    )
                }
            }.catch<Exception> {
                false
            }.execute()
        }
    }

    fun openBiometricCheck() {
        viewModelScope.launch {
            try {
                val isSuccess: Boolean =
                    biometryAuthenticator.checkBiometryAuthentication("Just for test", "Oops")
                if (isSuccess) {
                    _bioLogin.tryEmit(
                        Resource(
                            status = Status.SUCCESS,
                            data = true,
                            message = "",
                            error = null
                        )
                    )

                }
            } catch (throwable: Throwable) {
                logger.d("throwable $throwable")
            }
        }
    }

}