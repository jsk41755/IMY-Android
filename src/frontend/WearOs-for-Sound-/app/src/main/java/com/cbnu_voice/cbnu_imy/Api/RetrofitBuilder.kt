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
    var ttsApi: TtsApi

    val nestnetip= "http://113.198.137.188:81/"
    val houseip="http://113.198.137.200:4511"
    val localhost="http://10.0.2.2:5000"
    val pulseApihost = "http://172.17.0.4:8083"
    val nova="http://192.168.30.55:5000"
    val jungjuip="http://49.143.65.133:15605"
    init{
        println("connected")
        val retrofit= Retrofit.Builder()
            .baseUrl(jungjuip)   //요청 보내는 API 서버 url /로 끝나야 함
            .addConverterFactory(ScalarsConverterFactory.create() )
            .addConverterFactory(GsonConverterFactory.create())//Gson을 역직렬화
            .build()
        userapi=retrofit.create(UserApi::class.java)
        corpusapi=retrofit.create(CorpusApi::class.java)
        chatbotapi=retrofit.create(ChatbotApi::class.java)
        pulseApi = retrofit.create(PulseApi::class.java)
        ttsApi = retrofit.create(TtsApi::class.java)
    }
}