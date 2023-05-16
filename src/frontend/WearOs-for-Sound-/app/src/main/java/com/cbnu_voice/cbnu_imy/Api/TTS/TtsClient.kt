package com.cbnu_voice.cbnu_imy.Api.TTS

import retrofit2.Retrofit

object TtsClient {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://49.143.65.133:15605/")
        .build()

    val apiService = retrofit.create(TtsService::class.java)
}