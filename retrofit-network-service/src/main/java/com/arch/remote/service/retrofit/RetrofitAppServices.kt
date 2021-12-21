package com.arch.remote.service.retrofit

import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface RetrofitAppServices {
    @GET("assets/bankassist/{identifier}.json")
    fun getUserInformation(@Path("identifier") userIdentifier: String):
            Response
}