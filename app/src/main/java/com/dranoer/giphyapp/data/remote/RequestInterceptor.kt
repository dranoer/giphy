package com.dranoer.giphyapp.data.remote

import com.dranoer.giphyapp.Constants
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", Constants.API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}