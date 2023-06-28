package com.tecsup.edu.healthylife.data

//import java.time.ZonedDateTime

data class CitaMedica(
    val id: Int,
    val id_paciente: Int,
    val id_doctor: Int,
    val estado: Boolean,
    val fecha_cita_creada: String,
    val fecha_de_cita: String,
    val triaje: Boolean
)


