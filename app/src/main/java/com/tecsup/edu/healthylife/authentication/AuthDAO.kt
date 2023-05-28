package com.tecsup.edu.healthylife.authentication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AuthDAO {
    @Insert
    fun registerUser(authEntity: AuthEntity)

    @Query("SELECT * FROM auth_table WHERE email = :email AND password = :password")
    fun loginUser(email: String, password: String): AuthEntity?
}
