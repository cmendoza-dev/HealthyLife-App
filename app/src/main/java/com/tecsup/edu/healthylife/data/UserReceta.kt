package com.tecsup.edu.healthylife.data

data class UserReceta(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val dni: Int,
    val email: String,
    val direccion: String,
    val telefono: Int,
    val password: String,
    val especialidad: String,
    val id_user: Int
)
