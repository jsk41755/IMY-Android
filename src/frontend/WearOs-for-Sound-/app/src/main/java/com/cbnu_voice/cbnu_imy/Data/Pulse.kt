package com.cbnu_voice.cbnu_imy.Data

class Pulse constructor(time : String, value : String) {
    var createdTime: String
    var pulseValue: String

    init {
        this.createdTime = time
        this.pulseValue = value
    }
}