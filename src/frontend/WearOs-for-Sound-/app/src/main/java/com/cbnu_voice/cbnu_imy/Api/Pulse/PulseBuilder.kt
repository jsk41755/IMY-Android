package com.cbnu_voice.cbnu_imy.Api.Pulse

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object PulseBuilder {
    var pulseApi: PulseApi
    val localhost="http://172.17.0.4:8083"
    init{
        println("connected")
        val retrofit= Retrofit.Builder()
            .baseUrl(localhost)
            .addConverterFactory(ScalarsConverterFactory.create() )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        pulseApi =retrofit.create(PulseApi::class.java)
    }
}