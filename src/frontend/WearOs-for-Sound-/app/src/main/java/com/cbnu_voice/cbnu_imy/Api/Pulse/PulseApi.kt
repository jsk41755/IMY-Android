package com.cbnu_voice.cbnu_imy.Api.Pulse

import com.cbnu_voice.cbnu_imy.Dto.Pulse.DailyPulseDto
import com.cbnu_voice.cbnu_imy.Dto.Pulse.Pulse
import retrofit2.Call
import retrofit2.http.*
import java.time.LocalDate

public interface PulseApi {

    /**
     * POST 방식, @POST(URI)
     */
    @POST("/pulse")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun postPulseResponse(
        @Body pulse: String
    ): Call<DailyPulseDto>

    /**
     * Get 방식 @Get(URI)
     */
    @GET("/pulse")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun getPulseResponse(): Call<DailyPulseDto>


}