package com.tecsup.edu.healthylife.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tecsup.edu.healthylife.data.User
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException

class LoginViewModel : ViewModel() {
    val loginSuccessLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private val client: OkHttpClient = OkHttpClient()

    fun login(email: String, password: String) {
        val url = "http://192.168.43.109:8000/api/users/"
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val users = parseUsers(responseBody)

                val isAuthenticated = users.any { user ->
                    user.email == email && user.password == password
                }

                loginSuccessLiveData.postValue(isAuthenticated)
            }

            override fun onFailure(call: Call, e: IOException) {
                loginSuccessLiveData.postValue(false)
            }
        })
    }

    private fun parseUsers(responseBody: String?): List<User> {
        val users = mutableListOf<User>()

        responseBody?.let {
            try {
                val jsonArray = JSONArray(it)

                for (i in 0 until jsonArray.length()) {
                    val jsonUser = jsonArray.getJSONObject(i)

                    val idUser = jsonUser.getInt("id_user")
                    val nombre = jsonUser.getString("nombre")
                    val apellido = jsonUser.getString("apellido")
                    val dni = jsonUser.getInt("dni")
                    val email = jsonUser.getString("email")
                    val direccion = jsonUser.getString("direccion")
                    val telefono = jsonUser.getInt("telefono")
                    val password = jsonUser.getString("password")
                    val especialidad = jsonUser.getString("especialidad")

                    val user = User(
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

                    users.add(user)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        return users
    }
}

