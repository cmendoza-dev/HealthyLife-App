package com.tecsup.edu.healthylife.repository

import com.tecsup.edu.healthylife.data.User
import com.tecsup.edu.healthylife.utils.ApiClientes
import com.tecsup.edu.healthylife.utils.ApiService
import okhttp3.ResponseBody
import retrofit2.Response

class UserRepository {
    private val apiService: ApiService = ApiClientes.create()
    suspend fun registerUser(user: User): Response<ResponseBody> {
        return apiService.registerUser(user)
    }
}
