package com.cbnu_voice.cbnu_imy.Api.Pulse

import android.text.format.Time
import android.util.Log
import com.cbnu_voice.cbnu_imy.BuildConfig
import com.cbnu_voice.cbnu_imy.Utils.Constans.TAG
import com.cbnu_voice.cbnu_imy.Utils.isJasonArray
import com.cbnu_voice.cbnu_imy.Utils.isJsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
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

    /*var pulseApi: PulseApi
    val localhost="http://49.143.65.133:15606"
    init{
        println("connected")
        val retrofit= Retrofit.Builder()
            .baseUrl(localhost)
            .addConverterFactory(ScalarsConverterFactory.create() )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        pulseApi =retrofit.create(PulseApi::class.java)
    }*/
}