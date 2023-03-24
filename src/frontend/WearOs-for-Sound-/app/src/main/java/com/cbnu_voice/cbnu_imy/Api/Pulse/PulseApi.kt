package com.cbnu_voice.cbnu_imy.Api.Pulse

import com.cbnu_voice.cbnu_imy.Dto.Pulse.DailyPulseDto
import retrofit2.Call
import retrofit2.http.*

public interface PulseApi {

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


}