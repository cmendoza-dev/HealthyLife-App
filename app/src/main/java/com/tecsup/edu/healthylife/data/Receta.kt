package com.tecsup.edu.healthylife.data

data class Receta(
    val id: Int,
    val diagnostico: String,
    val indicaciones: String,
    val recomendacion: String,
    val id_cita: Int
)
