package com.tecsup.edu.healthylife.view_model

import androidx.lifecycle.ViewModel
import com.tecsup.edu.healthylife.data.Cita
import com.tecsup.edu.healthylife.utils.ApiService
import com.tecsup.edu.healthylife.utils.ConfigIP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CitaViewModel : ViewModel() {

    private val ip = ConfigIP.IP
    private val api: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://$ip:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiService::class.java)
    }

    suspend fun registrarCita(cita: Cita) {
        withContext(Dispatchers.IO) {
            try {
                api.registrarCita(cita)
                // The appointment was registered successfully
            } catch (e: Exception) {
                // An error occurred while registering the appointment
                e.printStackTrace()
            }
        }
    }
}
