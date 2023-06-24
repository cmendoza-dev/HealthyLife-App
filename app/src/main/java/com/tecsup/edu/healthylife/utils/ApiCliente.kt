package com.tecsup.edu.healthylife.utils

import com.tecsup.edu.healthylife.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

object ApiCliente {
    private const val BASE_URL = "http://192.168.43.109:8000/api/users/"

    suspend fun login(email: String, password: String): User? {
        val client = OkHttpClient()
        val requestBody: RequestBody = FormBody.Builder()
            .add("email", email)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url(BASE_URL)
            .post(requestBody)
            .build()

        val response = withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }

        if (response.isSuccessful) {
            val responseData = response.body()?.string()
            return parseUser(responseData)
        } else {
            return null
        }
    }

    private fun parseUser(responseData: String?): User? {
        try {
            val jsonObject = JSONObject(responseData)
            val idUser = jsonObject.getInt("id_user")
            val nombre = jsonObject.getString("nombre")
            val apellido = jsonObject.getString("apellido")
            val dni = jsonObject.getInt("dni")
            val email = jsonObject.getString("email")
            val direccion = jsonObject.getString("direccion")
            val telefono = jsonObject.getInt("telefono")
            val password = jsonObject.getString("password")
            val especialidad = jsonObject.getString("especialidad")

            return User(
                idUser,
                nombre,
                apellido,
                dni,
                email,
                direccion,
                telefono,
                password,
                especialidad
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
