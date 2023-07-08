package com.cbnu_voice.cbnu_imy.Api.Pulse

import com.cbnu_voice.cbnu_imy.Dto.Pulse.PulseDto
import com.cbnu_voice.cbnu_imy.Utils.API
import com.google.gson.JsonElement
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface PulseApi {
    @POST("/pulse/total")
    @Headers("accept: application/json", "content-type: application/json")
    fun postPulseResponse(
        @Body pulse: PulseDto
    ): Call<ResponseBody>

    /**
     * Get 방식 @Get(URI)
     */
    @GET("/pulse")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun getPulseResponse(): Call<List<PulseDto>>
    @GET(API.GET_PULSE_AVG)
    fun getPulseAvgResponse(): Call<JsonElement>


}