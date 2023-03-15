package com.cbnu_voice.cbnu_imy.Api


import com.cbnu_voice.cbnu_imy.Dto.ChatbotDto
import retrofit2.Call
import retrofit2.http.*

interface ChatbotApi {


    @GET("/chatbot/g")
    fun getKogpt2Response(
        @Query("s") s:String
    ): Call<ChatbotDto>


    @GET("/")
    fun getHomeResponse(
    ): Call<String>
}