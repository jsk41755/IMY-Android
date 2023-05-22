package com.cbnu_voice.cbnu_imy.Api.Clova

import retrofit2.Retrofit

object ClovaClient {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://naveropenapi.apigw.ntruss.com/")
        .build()

    val naverTtsService = retrofit.create(ClovaApiService::class.java)

}