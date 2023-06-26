package com.tecsup.edu.healthylife.utils

import com.tecsup.edu.healthylife.data.CitaMedica
import okhttp3.ResponseBody
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.POST

interface CitasApiService {
    @POST("citas/")
    suspend fun registrarCita(@Body cita: CitaMedica): Response<ResponseBody>
}
