package com.example.tolkachev.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .header("x-api-key", "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b").build()

        return chain.proceed(request)
    }
}