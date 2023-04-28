package com.cbnu_voice.cbnu_imy.Api.Pulse

import com.cbnu_voice.cbnu_imy.Dto.Pulse.DailyPulseDto
import com.cbnu_voice.cbnu_imy.Utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

interface PulseApi {

    /**
     * POST 방식, @POST(URI)
     */
    @POST("/pulse")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun postPulseResponse(
        @Body pulse: DailyPulseDto
    ): Call<DailyPulseDto>

    /**
     * Get 방식 @Get(URI)
     */
    @GET("/pulse")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun getPulseResponse(): Call<List<DailyPulseDto>>
    @GET(API.GET_PULSE_AVG)
    fun getPulseAvgResponse(): Call<JsonElement>


}