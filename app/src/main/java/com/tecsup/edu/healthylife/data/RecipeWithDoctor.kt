package com.tecsup.edu.healthylife.data

data class RecipeWithDoctor(
    val id: Int,
    val diagnostico: String,
    val indicaciones: String,
    val recomendacion: String,
    val idCita: Int,
    val doctorNombre: String,
    val doctorEspecialidad: String
)
