package com.cbnu_voice.cbnu_imy.Api.Pulse

import com.cbnu_voice.cbnu_imy.Dto.Pulse.PulseDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PulseApi {
    @POST("/pulse/total")
    @Headers("accept: application/json", "content-type: application/json")
    fun postPulseResponse(
        @Body pulse: PulseDto
    ): Call<ResponseBody>

    @GET("/pulse/all/pulse/avg")
    suspend fun getPulseAvgResponse(): Response<ResponseBody>

    @GET("/pulse/all/str/cnt")
    suspend fun getPulseAboveResponse(): Response<ResponseBody>

}