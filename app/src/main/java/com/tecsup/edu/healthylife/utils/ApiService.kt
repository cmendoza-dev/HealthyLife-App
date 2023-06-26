package com.tecsup.edu.healthylife.utils

import com.tecsup.edu.healthylife.data.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("users/")
    suspend fun registerUser(@Body user: User): Response<ResponseBody>

    @GET("users/")
    fun getUsers(): Call<List<User>>

}
