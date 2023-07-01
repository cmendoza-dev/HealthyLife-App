package com.tecsup.edu.healthylife.view_model

import androidx.lifecycle.ViewModel
import com.tecsup.edu.healthylife.data.Cita
import com.tecsup.edu.healthylife.utils.CitaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CitaViewModel : ViewModel() {
    private val api: CitaApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.9:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(CitaApi::class.java)
    }

    suspend fun registrarCita(cita: Cita) {
        withContext(Dispatchers.IO) {
            try {
                api.registrarCita(cita)
                // La cita se registró exitosamente
            } catch (e: Exception) {
                // Ocurrió un error al registrar la cita
                e.printStackTrace()
            }
        }
    }
}
