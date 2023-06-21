package com.tecsup.edu.healthylife.login


import com.tecsup.edu.healthylife.network.ApiService
import com.tecsup.edu.healthylife.network.LoginRequest
import com.tecsup.edu.healthylife.network.LoginResponse
import io.reactivex.rxjava3.core.Single

class LoginRepository {

    private val api = ApiService().apiService

    fun login(email: String, pass: String): Single<LoginResponse> {
        return api.login(LoginRequest(email, pass))
    }

}

