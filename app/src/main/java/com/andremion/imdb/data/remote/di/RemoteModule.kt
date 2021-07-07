package com.andremion.imdb.data.remote.di

import com.andremion.imdb.BuildConfig
import com.andremion.imdb.data.remote.ImdbService
import com.andremion.imdb.data.remote.auth.AuthInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

private const val API_URL = "https://${BuildConfig.API_HOST}"

@Module
object RemoteModule {

    @Provides
    @Singleton
    fun providesImdbService(retrofitBuilder: Retrofit.Builder): ImdbService =
        retrofitBuilder
            .baseUrl(API_URL)
            .build()
            .create(ImdbService::class.java)

    @Provides
    fun providesRetrofitBuilder(client: OkHttpClient): Retrofit.Builder {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
    }

    @Provides
    fun providesOkHttpClient(
        level: HttpLoggingInterceptor.Level,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(level)
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun providesLoggingLevel(): HttpLoggingInterceptor.Level =
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
}
