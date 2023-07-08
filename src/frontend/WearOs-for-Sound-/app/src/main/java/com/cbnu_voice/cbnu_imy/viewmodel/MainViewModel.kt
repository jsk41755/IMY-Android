package com.cbnu_voice.cbnu_imy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cbnu_voice.cbnu_imy.Api.Pulse.PulseApi
import com.cbnu_voice.cbnu_imy.Api.Pulse.PulseClient
import com.cbnu_voice.cbnu_imy.Data.PulseData
import com.cbnu_voice.cbnu_imy.Dto.Pulse.PulseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.max

class MainViewModel : ViewModel() {
    private val _data = MutableLiveData<String>()
    var data: LiveData<String> = _data

    private val _bpmCount = MutableLiveData(0)
    var bpmCount: LiveData<Int> = _bpmCount

    private val _dataList = mutableListOf<Int>()

    private val _getCurrentTime = MutableStateFlow("")
    private val getCurrentTime: StateFlow<String> get() = _getCurrentTime

    private val _getCurrentDate = MutableStateFlow("")
    private val getCurrentDate: StateFlow<String> get() = _getCurrentDate

    fun bpmCount(count: Int){
        if(_bpmCount.value?.toInt()!! < 5){
            _bpmCount.value = _bpmCount.value!! + count
            _dataList.add(data.value!!.toInt())
            Log.d("bpmCount", bpmCount.value.toString())
        } else{
            currentTime()
            viewModelScope.launch(Dispatchers.IO) {
                calculateAverage()
            }
            _bpmCount.value = 0
            Log.d("bpmCount2", bpmCount.value.toString())
        }
    }

    private fun currentTime() {
        val currentDate = LocalDateTime.now()
        val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        _getCurrentDate.value = currentDate.format(dateFormatter)

        val currentTime = LocalDateTime.now()
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        _getCurrentTime.value = currentTime.format(timeFormatter)
    }

    private fun calculateAverage() {
        val sum = _dataList.sum()
        val average = sum / _dataList.size

        val pulseData = listOf(PulseData(getCurrentTime.value, average.toString()))
        val pulseDto = PulseDto(createdDate = getCurrentDate.value, pulseList = pulseData)

        postPulseData(pulseDto)
    }

    private fun postPulseData(pulseItem: PulseDto) {
        try {
            val pulseApi: PulseApi? = PulseClient.getClient()?.create(PulseApi::class.java)
            val response: Response<ResponseBody>? = pulseApi?.postPulseResponse(pulseItem)?.execute()

            if (response != null && response.isSuccessful) {
                Log.d("postPulseData", "Post 요청 성공")
            } else {
                val errorBody = response?.errorBody()?.string()
                Log.d("postPulseData", "API 호출 실패, 오류 메시지: $errorBody")
            }
        } catch (e: Exception) {
            Log.e("postPulseData", "API 호출 실패: ${e.message}")
        }
    }

    fun setBpm(numberBpm : String?){
        var intData = numberBpm?.toInt()

        _data.value = intData.toString()
    }
}