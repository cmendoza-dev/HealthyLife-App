package com.tecsup.edu.healthylife.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.edu.healthylife.data.Cita
import com.tecsup.edu.healthylife.utils.ApiCliente
import kotlinx.coroutines.launch

class CitaMedicaViewModel : ViewModel() {
    private val citasApiService = ApiCliente.citasApiService

    fun registrarCitaMedica(cita: Cita) {
        viewModelScope.launch {
            try {
                val response = citasApiService.registrarCita(cita)
                if (response.isSuccessful) {
                    // Medical appointment registered successfully
                    // Realizar cualquier acción necesaria aquí
                } else {
                    // An error occurred while registering the medical appointment
                    // Manejar el error según sea necesario
                }
            } catch (e: Exception) {
                // An exception occurred while making the request
                // Manejar la excepción según sea necesario
            }
        }
    }
}
