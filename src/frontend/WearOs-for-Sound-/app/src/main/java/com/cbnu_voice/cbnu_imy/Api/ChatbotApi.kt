package com.cbnu_voice.cbnu_imy.Api

import com.cbnu_voice.cbnu_imy.Data.EmotionCount
import com.cbnu_voice.cbnu_imy.Dto.ChatbotDto
import retrofit2.Call
import retrofit2.http.*

interface ChatbotApi {
    @GET("/chatbot/b")
    fun getKobertResponse(
        @Query("s") s:String
    ): Call<ChatbotDto>

    @GET("emotions/count")
    suspend fun getEmotionsCount(): List<EmotionCount>
}