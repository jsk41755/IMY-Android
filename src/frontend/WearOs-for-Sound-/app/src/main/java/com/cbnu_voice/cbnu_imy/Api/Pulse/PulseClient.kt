package com.cbnu_voice.cbnu_imy.Api.Pulse

import android.text.format.Time
import android.util.Log
import com.cbnu_voice.cbnu_imy.Utils.Constans.TAG
import com.cbnu_voice.cbnu_imy.Utils.isJasonArray
import com.cbnu_voice.cbnu_imy.Utils.isJsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object PulseClient {
    private var retrofitClient: Retrofit? = null
    fun getClient(baseUrl: String):Retrofit?{
        Log.d("retrofit", "RetrofitClient - getClient() called")

        //okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        //로그를 찍기 위해 로깅 인터셉터 설정
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d(TAG, "loggingInterceptor: $message")

            when {
                message.isJsonObject() ->
                    Log.d(TAG, JSONObject(message).toString(4))
                message.isJasonArray() ->
                    Log.d(TAG, JSONArray(message).toString(4))
                else -> {
                    try {
                        Log.d(TAG, JSONObject(message).toString(4))
                    } catch (e: java.lang.Exception) {
                        Log.d(TAG, message)
                    }
                }
            }
        }

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        //위에서 설정한 로깅 인터셉터를 okhttp 클라이언트에 추가한다.
        client.addInterceptor(loggingInterceptor)

        //커텍션 타임아웃
        client.connectTimeout(10,TimeUnit.SECONDS)
        client.readTimeout(10, TimeUnit.SECONDS)
        client.writeTimeout(10, TimeUnit.SECONDS)
        client.retryOnConnectionFailure(true)


        if(retrofitClient == null){
            //레트로핏 빌더를 통해 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                //위에서 설정한 클라이언트로 레트로핏 클라이언트를 설정한다.
                .client(client.build())
                .build()
        }
        return retrofitClient
    }

    /*var pulseApi: PulseApi
    val localhost="http://49.143.65.133:15606"
    init{
        println("connected")
        val retrofit= Retrofit.Builder()
            .baseUrl(localhost)
            .addConverterFactory(ScalarsConverterFactory.create() )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        pulseApi =retrofit.create(PulseApi::class.java)
    }*/
}