package com.tecsup.edu.healthylife.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.edu.healthylife.data.Cita
import com.tecsup.edu.healthylife.data.Doctor
import com.tecsup.edu.healthylife.repository.CitasRepository
import kotlinx.coroutines.launch

class CitasViewModel : ViewModel() {
    private val citasRepository = CitasRepository()

    private val _citasList = MutableLiveData<List<Cita>>()
    val citasList: LiveData<List<Cita>> = _citasList

    private val _doctoresList = MutableLiveData<List<Doctor>>()
    val doctoresList: LiveData<List<Doctor>> = _doctoresList

    fun fetchData() {
        viewModelScope.launch {
            val citas = citasRepository.getCitas()
            val doctores = citasRepository.getDoctores()

            // Agregar registros de depuración para verificar los datos obtenidos
            Log.d("CitasViewModel", "Tamaño de citas: ${citas.size}")
            Log.d("CitasViewModel", "Tamaño de doctores: ${doctores.size}")

            citas.forEach { cita ->
                val doctor = doctores.find { it.id == cita.id_doctor }
                if (doctor != null) {
                    Log.d(
                        "CitasViewModel",
                        "Cita: ${cita.id_doctor}, Doctor: ${doctor.nombre} ${doctor.apellido}"
                    )
                    cita.id_doctor = doctor.id
                } else {
                    Log.e("CitasViewModel", "No se encontró el doctor para la cita")
                }
            }

            _citasList.value = citas
            _doctoresList.value = doctores
        }

    }
}


