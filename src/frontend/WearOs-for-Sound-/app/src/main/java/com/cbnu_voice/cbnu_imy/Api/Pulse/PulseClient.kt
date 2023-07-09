package com.cbnu_voice.cbnu_imy.Api.Pulse

import com.cbnu_voice.cbnu_imy.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object PulseClient {
    private var retrofitClient: Retrofit? = null
    private const val PULSE_URL = BuildConfig.PULSE_API
    fun getClient(): Retrofit? {
        if (retrofitClient == null) {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()

            retrofitClient = Retrofit.Builder()
                .baseUrl(PULSE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitClient
    }
}