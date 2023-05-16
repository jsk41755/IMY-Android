package com.cbnu_voice.cbnu_imy.Api.TTS

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming

interface TtsService{
    @Streaming
    @GET("tts-server/api/gowtts")
    fun getTTSStream(@Query("text") text: String): Call<ResponseBody>
}