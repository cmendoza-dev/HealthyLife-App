package com.tecsup.edu.healthylife.utils

import com.tecsup.edu.healthylife.data.User
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val baseUrl = "http://192.168.43.109:8000/api/"
    private val retrofit: Retrofit

    init {
        val client = OkHttpClient.Builder().build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getUsers(callback: Callback<List<User>>) {
        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getUsers()
        call.enqueue(callback)
    }

}
