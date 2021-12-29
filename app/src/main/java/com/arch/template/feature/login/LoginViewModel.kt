package com.arch.template.feature.login

import androidx.lifecycle.viewModelScope
import com.arch.template.base.BaseViewModel
import com.arch.template.util.RequestManager
import com.core.entity.User
import com.core.error.BaseError
import com.core.repository.UserRepository
import com.core.utils.Either
import com.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

    private val _tokenFlow: MutableSharedFlow<Resource<User>> = MutableSharedFlow()

    val tokenFlow: SharedFlow<Resource<User>> = _tokenFlow

    fun doLogin(email: String, password: String) {
        viewModelScope.launch {
            object : RequestManager<User>() {
                override suspend fun createCall(): Either<BaseError, User> {
                    return userRepository.login(email, password)
                }
            }
                .asFlow().collect {
                    _tokenFlow.emit(it)
                }
        }
    }
}