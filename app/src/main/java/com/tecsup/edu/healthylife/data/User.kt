package com.tecsup.edu.healthylife.data

data class User(
    val id_user: Int,
    val nombre: String,
    val apellido: String,
    val dni: Int,
    val email: String,
    val direccion: String,
    val telefono: Int,
    val password: String,
    val especialidad: String
)

