package com.arch.data.entity.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val guid: String = "",
    val userName: String = "",
)
