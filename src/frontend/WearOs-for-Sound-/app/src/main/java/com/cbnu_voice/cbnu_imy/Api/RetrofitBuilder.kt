package com.cbnu_voice.cbnu_imy.Api

import com.cbnu_voice.cbnu_imy.Api.Pulse.PulseApi
import com.cbnu_voice.cbnu_imy.BuildConfig
import com.cbnu_voice.cbnu_imy.Data.EmotionCount
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    var chatBotApi: ChatbotApi
    var pulseApi: PulseApi

    private const val chatServer = BuildConfig.NOVA
    init{
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        val retrofit= Retrofit.Builder()
            .baseUrl(chatServer)   //요청 보내는 API 서버 url /로 끝나야 함
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create() )
            .addConverterFactory(GsonConverterFactory.create())//Gson을 역직렬화
            .build()

        chatBotApi=retrofit.create(ChatbotApi::class.java)
        pulseApi = retrofit.create(PulseApi::class.java)
    }

    suspend fun getEmotionsCount(): List<EmotionCount> {
        return chatBotApi.getEmotionsCount()
    }
}