package com.tecsup.edu.healthylife.utils

import com.tecsup.edu.healthylife.data.LoginRequest
import com.tecsup.edu.healthylife.data.LoginResponse
import com.tecsup.edu.healthylife.data.RegisterRequest
import com.tecsup.edu.healthylife.data.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiClient {
    @POST("users/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("users/")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>
}


