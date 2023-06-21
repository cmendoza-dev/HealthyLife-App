package com.tecsup.edu.healthylife.utils

import com.tecsup.edu.healthylife.data.AuthResponse
import com.tecsup.edu.healthylife.data.LoginBody
import com.tecsup.edu.healthylife.data.RegisterBody
import com.tecsup.edu.healthylife.data.UniqueEmailValidationResponse
import com.tecsup.edu.healthylife.data.ValidateEmailBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIConsumer {
    @POST("api/users/")
    suspend fun validateEmailAddress(@Body body: ValidateEmailBody): Response<UniqueEmailValidationResponse>

    @POST("api/users/")
    suspend fun registerUser(@Body body: RegisterBody): Response<AuthResponse>

    @POST("api/users/")
    suspend fun loginUser(@Body body: LoginBody): Response<AuthResponse>

}


