package com.tecsup.edu.healthylife.data

data class Cita(
    val id_cita: Int,
    val id_paciente: Int,
    var id_doctor: Int,
    val estado: Boolean,
    val fecha_cita_creada: String,
    val fecha_de_cita: String,
    val triaje: Boolean
)
