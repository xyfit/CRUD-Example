package com.example.restapiexample.api

import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("app-id", "63f4644d95e5a789f3274617")
            .build()
        return chain.proceed(request)
    }
}