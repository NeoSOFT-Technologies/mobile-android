package com.arch.presentation.model

import com.arch.entity.User
import com.arch.utils.mapper.BaseLayerDataTransformer

data class UserModel(
    val token: String?
) : BaseLayerDataTransformer<UserModel, User>() {

    override fun restore(data: User): UserModel {
        return UserModel(data.token)
    }
}