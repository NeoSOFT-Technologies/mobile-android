package com.arch.data.entity.remote.response

import com.arch.data.entity.local.UserEntity
import com.arch.utils.mapper.BaseLayerDataTransformer
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponseEntity(
    @Json(name = "token")
    val token: String?
): BaseLayerDataTransformer<UserResponseEntity, UserEntity>(){
    override fun transform(): UserEntity {
        return UserEntity(token = token?:"")
    }
}