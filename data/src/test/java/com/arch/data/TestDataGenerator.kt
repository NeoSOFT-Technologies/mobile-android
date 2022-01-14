package com.arch.data

import com.arch.data.entity.request.LoginRequestEntity

class TestDataGenerator {
    companion object {
        fun getEmail(): String {
            return "test@gmail.com"
        }

        fun getPassword(): String {
            return "123456"
        }

        fun getToken(): String {
            return "asbrasktlan"
        }

        fun getLoginRequestEntity(): LoginRequestEntity {
            return LoginRequestEntity(getEmail(), getToken())
        }
    }
}