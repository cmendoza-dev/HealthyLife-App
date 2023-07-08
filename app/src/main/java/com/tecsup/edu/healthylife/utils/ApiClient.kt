package com.tecsup.edu.healthylife.utils

import com.tecsup.edu.healthylife.data.User
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val ip = ConfigIP.IP
    private val baseUrl = "http://$ip:8000/api/"
    private val client = OkHttpClient.Builder().build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    fun getUsers(callback: Callback<List<User>>) {
        val call = apiService.getUsers()
        call.enqueue(callback)
    }
}
