package com.example.gofit_10712.database

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {

    private fun getRetrofitClient(): Retrofit{
        return  Retrofit.Builder()
            .baseUrl("http://192.168.2.43:8000/gofit/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(): ApiClient{
        return getRetrofitClient().create(ApiClient::class.java)
    }
}