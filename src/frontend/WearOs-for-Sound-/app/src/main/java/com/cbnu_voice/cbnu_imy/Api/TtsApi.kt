package com.cbnu_voice.cbnu_imy.Api

import com.cbnu_voice.cbnu_imy.Dto.CorpusDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface TtsApi {

    @GET("/tts-server/api/glowtts")
    @Streaming
    suspend fun downloadWavFile(
        @Query("text") text : String
    ) : Call<ResponseBody>

}