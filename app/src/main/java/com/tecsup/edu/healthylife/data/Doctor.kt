package com.tecsup.edu.healthylife.data

data class Doctor(
    val idUser: Int,
    val nombre: String,
    val apellido: String,
    val dni: Int,
    val email: String,
    val direccion: String,
    val telefono: Long,
    val password: String,
    val especialidad: String
)

