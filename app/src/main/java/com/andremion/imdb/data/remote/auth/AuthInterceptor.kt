package com.andremion.imdb.data.remote.auth

import com.andremion.imdb.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("x-rapidapi-host", BuildConfig.API_HOST)
            .addHeader("x-rapidapi-key", BuildConfig.API_KEY)
            .build()
        return chain.proceed(request)
    }
}
