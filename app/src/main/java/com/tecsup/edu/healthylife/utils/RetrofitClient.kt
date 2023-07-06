package com.tecsup.edu.healthylife.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val ip = ConfigIP.IP

    private const val BASE_URL = "http://$ip:8000/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun create(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}

