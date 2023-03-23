package com.cbnu_voice.cbnu_imy.Api.Pulse

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object PulseBuilder {
    var pulseApi: PulseApi
    val localhost="http://localhost:8083"
    init{
        val retrofit= Retrofit.Builder()
            .baseUrl(localhost)
            .addConverterFactory(ScalarsConverterFactory.create() )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        pulseApi =retrofit.create(PulseApi::class.java)
    }
}