package com.cbnu_voice.cbnu_imy.Api.Pulse

import android.util.Log
import com.cbnu_voice.cbnu_imy.Utils.API
import com.cbnu_voice.cbnu_imy.Utils.Constans.TAG
import com.cbnu_voice.cbnu_imy.Utils.RESPONSE_STATE
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object{
        val instance = RetrofitManager()
    }

    //레트로핏 인터페이스 가져오기
    private val pulseApi : PulseApi? = PulseClient.getClient(API.Base_URL)?.create(PulseApi::class.java)

    //daily bpm avg api 호출
    fun getBpmAvg(completion: (RESPONSE_STATE, String) -> Unit){

        val call = pulseApi?.getPulseAvgResponse() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{

            //응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "onResponse: ${response.body()}")

                response.body()?.let {
                    val body = it.asJsonArray.asJsonObject.getAsJsonObject()
                    val averageValue = body.asJsonObject.getAsJsonObject("createdDate")
                    //val result = body2.getAsJsonObject("averageValue")

                    //val total = averageValue.getAsJsonObject("avgValue")

                    Log.d(TAG, "onResponse: $averageValue")
                }
                //completion(RESPONSE_STATE.OKAY, response.body().toString())
            }

            //응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "onFailure: onFailure")
                completion(RESPONSE_STATE.FAIL, t.toString())
            }

        })
    }
}