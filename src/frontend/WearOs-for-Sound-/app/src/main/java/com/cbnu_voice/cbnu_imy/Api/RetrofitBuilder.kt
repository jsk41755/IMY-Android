package com.cbnu_voice.cbnu_imy.Api

import com.cbnu_voice.cbnu_imy.Api.Pulse.PulseApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitBuilder {
    var userapi: UserApi
    var corpusapi: CorpusApi
    var chatbotapi: ChatbotApi
    var pulseApi: PulseApi

    val nestnetip= "http://113.198.137.188:81/"
    val houseip="http://113.198.137.200:4511"
    val localhost="http://10.0.2.2:5000"
    val pulseApihost = "http://172.17.0.4:8083"
    init{
        println("connected")
        val retrofit= Retrofit.Builder()
            .baseUrl(localhost)   //요청 보내는 API 서버 url /로 끝나야 함
            .addConverterFactory(ScalarsConverterFactory.create() )
            .addConverterFactory(GsonConverterFactory.create())//Gson을 역직렬화
            .build()
        userapi=retrofit.create(UserApi::class.java)
        corpusapi=retrofit.create(CorpusApi::class.java)
        chatbotapi=retrofit.create(ChatbotApi::class.java)
        pulseApi = retrofit.create(PulseApi::class.java)

    }
}