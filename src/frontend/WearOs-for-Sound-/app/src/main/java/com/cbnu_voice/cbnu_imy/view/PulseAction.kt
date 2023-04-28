package com.cbnu_voice.cbnu_imy.Ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.cbnu_voice.cbnu_imy.Api.Pulse.PulseClient
import com.cbnu_voice.cbnu_imy.Data.Pulse
import com.cbnu_voice.cbnu_imy.Dto.Pulse.DailyPulseDto
import com.cbnu_voice.cbnu_imy.databinding.ActivityPulseBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalTime

private lateinit var binding: ActivityPulseBinding
class PulseAction : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPulseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /**
         * post 테스트를 위한 click listner
         */
        binding.testPostPulse.setOnClickListener {

            // 날짜
            val date = LocalDate.now().toString()
            //시간
            val time = LocalTime.now().toString()
            //맥박값 2개
            val value1 = "140"
            val value2 = "110"

            //맥박이 들어온 시간과 값 1
            val pulse1 = Pulse(
                time,
                value1
            )
            //맥박이 들어온 시간과 값 2
            val pulse2 = Pulse(
                time,
                value2
            )

            //들어온 맥박을 묶어서 list 로 제작
            val plist = ArrayList<Pulse>()
            plist.add(pulse1)
            plist.add(pulse2)

            //맥박 리스트를 날짜와 묶어서 DailyPulseDto로 저장
            val test = DailyPulseDto(
                date,
                plist
            )

            // test 값을 넣어 주었다.
            //PostDailyPulse(test)
        }
        binding.testGetPulse.setOnClickListener {
            //GetPulseList() //맥박 조회
        }


    }

    /**
     * daily pulse를 post 할 때 사용되는 함수
     */
    /*fun PostDailyPulse(dailyPulse: DailyPulseDto){
        val textviewresult= binding.textViewResult
        val pulseList = PulseClient.pulseApi.postPulseResponse(dailyPulse)
        pulseList.enqueue(object : Callback<DailyPulseDto>{
            override fun onResponse(
                call: Call<DailyPulseDto>,
                response: Response<DailyPulseDto>
            ) {
                if(response.isSuccessful()){ // 응답 잘 받은 경우
                    Log.d("RESPONSE: ", response.body().toString())
                    textviewresult.setText(response.body().toString())
                }else{
                    // 통신 성공 but 응답 실패
                    Log.d("RESPONSE", "FAILURE")
                }
            }
            override fun onFailure(call: Call<DailyPulseDto>, t: Throwable) {
                // 통신에 실패한 경우
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
            }
        })

    }*/

    /**
     * 리스트 형식으로 DailyPulseDto 형식에 맞는 데이터를 가져온다.
     */
    /*fun GetPulseList(){
        val textviewresult= binding.textViewResult
        val pulseList = PulseClient.pulseApi.getPulseResponse()
        pulseList.enqueue(object : Callback<List<DailyPulseDto>> { // 비동기 방식 통신 메소드
            override fun onResponse( // 통신에 성공한 경우
                call: Call<List<DailyPulseDto>>,
                response: Response<List<DailyPulseDto>>
            ) {
                if(response.isSuccessful){ // 응답 잘 받은 경우
                    for (d in response.body()!!) {
                        val date = d.createdDate
                        val pulselist = d.pulseList


                    }
                    Log.d("respnose: ", response.body().toString())
                    textviewresult.text = response.body().toString()

                }else{
                    // 통신 성공 but 응답 실패
                    Log.d("RESPONSE", "FAILURE")
                }
            }

            override fun onFailure(call: Call<List<DailyPulseDto>>, t: Throwable) {
                // 통신에 실패한 경우
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
            }

        })
    }*/
}