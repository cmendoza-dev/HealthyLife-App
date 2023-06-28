package com.tecsup.edu.healthylife.view_model

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
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
    val authenticatedUserLiveData: MutableLiveData<User?> = MutableLiveData()

    private lateinit var context: Context
    private val client: OkHttpClient = OkHttpClient()

    fun setContext(context: Context) {
        this.context = context
    }

    fun getAuthenticatedUser(): User? {
        val userJson = getSharedPreferences()?.getString("user", null)
        return userJson?.let {
            Gson().fromJson(it, User::class.java)
        }
    }

    fun setAuthenticatedUser(user: User) {
        val userJson = Gson().toJson(user)
        getSharedPreferences()?.edit()?.putString("user", userJson)?.apply()
    }

    fun login(email: String, password: String) {
        val url = "http://192.168.43.109:8000/api/users/"
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val users = parseUsers(responseBody)

                val authenticatedUser = users.find { user ->
                    user.email == email && user.password == password
                }

                if (authenticatedUser != null) {
                    authenticatedUserLiveData.postValue(authenticatedUser)
                    loginSuccessLiveData.postValue(true)

                    // Guardar el usuario autenticado en el ViewModel
                    setAuthenticatedUser(authenticatedUser)
                } else {
                    loginSuccessLiveData.postValue(false)
                }
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

                    val id = jsonUser.getInt("id")
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
                        id,
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

    private fun getSharedPreferences(): SharedPreferences? {
        return context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    }
}
