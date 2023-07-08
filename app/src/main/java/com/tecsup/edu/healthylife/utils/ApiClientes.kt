package com.tecsup.edu.healthylife.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientes {

    private const val BASE_URL = "http://${ConfigIP.IP}:8000/api/"

    fun create(): ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}
