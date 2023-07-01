package com.tecsup.edu.healthylife.utils

import com.tecsup.edu.healthylife.data.Cita
import com.tecsup.edu.healthylife.data.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("users/")
    suspend fun registerUser(@Body user: User): Response<ResponseBody>

    @GET("users/")
    fun getUsers(): Call<List<User>>

    // Define el endpoint para obtener los datos del usuario/doctor por su id
    @GET("users/{id}")
    fun getUser(@Path("id") id: Int): Call<User>

    // Define el endpoint para obtener los datos de la cita por su id
    @GET("citas/{id}")
    fun getCita(@Path("id") id: Int): Call<Cita>


}
