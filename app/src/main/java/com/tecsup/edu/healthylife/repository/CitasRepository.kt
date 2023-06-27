package com.tecsup.edu.healthylife.repository

import com.tecsup.edu.healthylife.data.Cita
import com.tecsup.edu.healthylife.data.Doctor
import com.tecsup.edu.healthylife.utils.ApiClientes

class CitasRepository {
    private val apiService = ApiClientes.create()

    suspend fun getDoctores(): List<Doctor> {
        return apiService.getDoctores()
    }

    suspend fun getCitas(): List<Cita> {
        return apiService.getCitas()
    }
}
