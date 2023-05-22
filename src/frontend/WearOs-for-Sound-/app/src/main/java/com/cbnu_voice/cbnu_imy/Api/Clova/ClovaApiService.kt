package com.cbnu_voice.cbnu_imy.Api.Clova

import com.cbnu_voice.cbnu_imy.BuildConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ClovaApiService {
    @POST("tts-premium/v1/tts")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    suspend fun fetchAudioUrl(
        @Field("speaker") speaker: String,
        @Field("volume") volume: Int,
        @Field("speed") speed: Int,
        @Field("pitch") pitch: Int,
        @Field("format") format: String,
        @Field("text") text: String
    ): ResponseBody
}

