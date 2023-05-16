package com.cbnu_voice.cbnu_imy.Api.TTS


import com.cbnu_voice.cbnu_imy.Api.TTS.TtsClient.retrofit
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class TtsManager {
    val TtsService = retrofit.create(TtsService::class.java)

    val text = "대화" // 스트리밍할 TTS 텍스트

    fun getTts(){
        val url = "http://49.143.65.133:15605/tts-server/api/glowtts?text=대화"
        val connection = URL(url).openConnection() as HttpURLConnection

        val inputStream = connection.inputStream

        val call = TtsService.getTTSStream(text)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let { stream ->
                        try {
                            val bufferSize = 1024
                            val buffer = ByteArray(bufferSize)
                            var bytesRead : Int

                            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                                // 스트리밍된 데이터를 처리합니다.
                                // 예시: 데이터를 재생하거나 다른 처리 작업을 수행합니다.
                            }
                        } catch (e: IOException) {
                            // 스트리밍 중 오류가 발생한 경우에 대한 처리를 수행합니다.
                        } finally {
                            stream.close()
                        }
                    }
                } else {
                    // 요청이 실패한 경우에 대한 처리를 수행합니다.
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // 네트워크 오류 또는 예외 발생 시에 대한 처리를 수행합니다.
            }
        })
    }


}