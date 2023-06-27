package com.tecsup.edu.healthylife.utils

import com.tecsup.edu.healthylife.data.Cita
import retrofit2.http.Body
import retrofit2.http.POST

interface CitaApi {
    @POST("citas/")
    suspend fun registrarCita(@Body cita: Cita)
}
