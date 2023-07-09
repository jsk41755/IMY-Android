package com.cbnu_voice.cbnu_imy.Api.Pulse


class RetrofitManager {
    companion object{
        val instance = RetrofitManager()
    }

    //레트로핏 인터페이스 가져오기
    //private val pulseApi : PulseApi? = PulseClient.getClient(API.Base_URL)?.create(PulseApi::class.java)

    /*fun getBpmAvg(completion: (RESPONSE_STATE, String) -> Unit){
        val call = pulseApi?.getPulseAvgResponse() ?: return
        call.enqueue(object : retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "onResponse: ${response.body()}")

                if (response.isSuccessful) {
                    response.body()?.let {
                        val jsonArray = it.asJsonArray
                        for (i in 0 until jsonArray.size()) {
                            val jsonObject = jsonArray[i].asJsonObject
                            val createdDate = jsonObject.get("createdDate").asString
                            val averageValue = jsonObject.getAsJsonObject("averageValue")
                            val avgSeq = averageValue.get("avgSeq").asInt
                            val avgValue = averageValue.get("avgValue").asString

                            Log.d(TAG, "Created Date: $createdDate")
                            Log.d(TAG, "Average Value - Seq: $avgSeq")
                            Log.d(TAG, "Average Value - Value: $avgValue")
                        }
                    }
                } else {
                    Log.d(TAG, "onResponse: Error - ${response.message()}")
                }
            }


            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "onFailure: onFailure")
                completion(RESPONSE_STATE.FAIL, t.toString())
            }
        })
    }*/
}