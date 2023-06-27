package com.tecsup.edu.healthylife.utils

import com.tecsup.edu.healthylife.data.Cita
import com.tecsup.edu.healthylife.data.Doctor
import retrofit2.http.GET

interface ApiInterface {
    @GET("users")
    suspend fun getDoctores(): List<Doctor>

    @GET("citas")
    suspend fun getCitas(): List<Cita>
}
