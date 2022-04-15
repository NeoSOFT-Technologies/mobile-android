package com.arch.presentation.model

import com.arch.entity.User
import com.arch.utils.mapper.BaseLayerDataTransformer

data class UserPresentation(
    val token: String?
) : BaseLayerDataTransformer<UserPresentation, User>() {

    override fun restore(data: User): UserPresentation {
        return UserPresentation(data.token)
    }
}