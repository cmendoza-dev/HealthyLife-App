package com.tecsup.edu.healthylife.authentication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth_table")
data class AuthEntity(
    @PrimaryKey val id: String,
    val email: String,
    val password: String
)
