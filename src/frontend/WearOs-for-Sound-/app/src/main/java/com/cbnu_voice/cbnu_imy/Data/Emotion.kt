package com.cbnu_voice.cbnu_imy.Data

import com.google.gson.annotations.SerializedName

data class EmotionCount(
    @SerializedName("기쁨") val joy: Int,
    @SerializedName("당황") val surprise: Int,
    @SerializedName("분노") val anger: Int,
    @SerializedName("불안") val anxiety: Int,
    @SerializedName("우울") val sadness: Int
)

fun EmotionCount.getTotalCount(): Int {
    return joy + surprise + anger + anxiety + sadness
}

fun EmotionCount.getCountForEmotion(emotion: String): Int {
    return when (emotion) {
        "기쁨" -> joy
        "당황" -> surprise
        "분노" -> anger
        "불안" -> anxiety
        "우울" -> sadness
        else -> 0
    }
}

