package com.cbnu_voice.cbnu_imy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _data = MutableLiveData<String>()

    /***
     * activity bpm.
     */
    val data: LiveData<String> = _data

    private val _bpmStack = MutableLiveData<Int>()
    val bpmStack: LiveData<Int> = _bpmStack

    fun hasNoCheckBpm(): Boolean { //주문의 맛이 설정되었는지 여부 확인하는 메서드
        return _data.value.isNullOrEmpty()
    }

    fun getBpm(): LiveData<String> = data

    /**
     * get bpm to int
     */
    fun setBpm(numberBpm : String?){
        var intData = numberBpm?.toInt()
        Log.d("aaa", " ${data.value}")
        /*if (bpmStack > 10) {
            //startActivity(Intent(this, Chatbot::class.java).putExtra("stage", "refuse"))
        }

        if (intData.toFloat() > 40.0) {
            bpmStack++
        }*/

        _data.value = intData.toString()
    }

    /**
     * get post pulse.
     * @input : String bpm
     * @output : Retrofit
     */

    /**
     * send pulse
     */
    private fun getPulse(){

    }

    /**
     * get pulse
     */
    private fun SendPulse(){
        /**
         * pulse 기준.
         * 마지막 스택의 값을 전송.
         *
         * 하루동안 총 발생한 이상값, => column 에 저장
         * 하루동안 맥박의 평균값 => column 에 저장
         * 매 맥박 측정값 저장 => column 에 저장
         *
         * 전체 발생한 이상값
         * 전체 발생한 맥박의 평균값
         * 전체 맥박 측정값
         *
         * 24시간 측정한다는 가정하에 한시간당 측정, 하루 24번 측정.
         *
         */

    }

}