package com.cbnu_voice.cbnu_imy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var _data = MutableLiveData<String>()
    var data: LiveData<String> = _data

    private val _bpmStack = MutableLiveData<Int>(0)
    var bpmStack: LiveData<Int> = _bpmStack

    fun hasNoCheckBpm(): Boolean {
        return _data.value.isNullOrEmpty()
    }

    fun bpmStack(count : Int){
        if(_bpmStack.value?.toInt()!! < 10){
            _bpmStack.value = _bpmStack.value!! + count
            Log.d("stack1", _bpmStack.value.toString())
        }
        else{
            //_bpmStack.value = 0
            Log.d("stack2", _bpmStack.value.toString())
        }

    }

    fun getBpm(): LiveData<String> = data

    /**
     * get bpm to int
     */
    fun setBpm(numberBpm : String?){
        var intData = numberBpm?.toInt()

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