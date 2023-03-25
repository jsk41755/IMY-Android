package com.cbnu_voice.cbnu_imy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _data = MutableLiveData<String>()
    val data: LiveData<String> = _data

    private val _bpmStack = MutableLiveData<Int>()
    val bpmStack: LiveData<Int> = _bpmStack

    fun hasNoCheckBpm(): Boolean { //주문의 맛이 설정되었는지 여부 확인하는 메서드
        return _data.value.isNullOrEmpty()
    }

    fun getBpm(): LiveData<String> = data

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


}