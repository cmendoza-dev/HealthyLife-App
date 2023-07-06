package com.tecsup.edu.healthylife.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientes {

    private const val ip = ConfigIP.IP

    private const val BASE_URL = "http://$ip:8000/api/"

    fun create(): ApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiInterface::class.java)
    }
}
