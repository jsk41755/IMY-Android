package com.cbnu_voice.cbnu_imy.Time

import android.os.Handler
import android.os.Looper
import java.text.SimpleDateFormat
import java.util.*

class Clock(private val onUpdate: (String) -> Unit) {
    private val handler = Handler(Looper.getMainLooper())
    private val updateTimeRunnable = object : Runnable {
        override fun run() {
            val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            onUpdate(currentTime)
            handler.postDelayed(this, 1000) // 1초마다 업데이트
        }
    }

    fun start() {
        handler.post(updateTimeRunnable)
    }

    fun stop() {
        handler.removeCallbacks(updateTimeRunnable)
    }
}