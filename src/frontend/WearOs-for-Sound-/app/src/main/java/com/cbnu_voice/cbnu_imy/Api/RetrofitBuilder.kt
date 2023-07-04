package com.cbnu_voice.cbnu_imy.Api

import com.cbnu_voice.cbnu_imy.Api.Pulse.PulseApi
import com.cbnu_voice.cbnu_imy.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitBuilder {
    var chatbotapi: ChatbotApi
    var pulseApi: PulseApi

    val nova = BuildConfig.NOVA
    init{
        val retrofit= Retrofit.Builder()
            .baseUrl(nova)   //요청 보내는 API 서버 url /로 끝나야 함
            .addConverterFactory(ScalarsConverterFactory.create() )
            .addConverterFactory(GsonConverterFactory.create())//Gson을 역직렬화
            .build()
        chatbotapi=retrofit.create(ChatbotApi::class.java)
        pulseApi = retrofit.create(PulseApi::class.java)

    }
}