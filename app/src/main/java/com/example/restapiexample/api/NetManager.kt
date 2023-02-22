package com.example.restapiexample.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetManager {

    private const val BASE_URL = "https://dummyapi.io/data/v1/"

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()

    var retrofitService: ApiService? = null
    fun getInstance() : ApiService {
        if (retrofitService == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitService = retrofit.create(ApiService::class.java)
        }
        return retrofitService!!
    }
}