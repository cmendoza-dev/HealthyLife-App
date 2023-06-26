package com.tecsup.edu.healthylife.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.edu.healthylife.data.CitaMedica
import com.tecsup.edu.healthylife.utils.ApiCliente
import kotlinx.coroutines.launch

class CitaMedicaViewModel : ViewModel() {
    private val citasApiService = ApiCliente.citasApiService

    fun registrarCitaMedica(cita: CitaMedica) {
        viewModelScope.launch {
            try {
                val response = citasApiService.registrarCita(cita)
                if (response.isSuccessful) {
                    // Cita médica registrada exitosamente
                    // Realizar cualquier acción necesaria aquí
                } else {
                    // Ocurrió un error al registrar la cita médica
                    // Manejar el error según sea necesario
                }
            } catch (e: Exception) {
                // Ocurrió una excepción al realizar la solicitud
                // Manejar la excepción según sea necesario
            }
        }
    }
}
