package com.cbnu_voice.cbnu_imy.Dto.Pulse

import java.time.LocalDate
import java.time.LocalTime

data class Pulse (
    val createdTime: String,
    val pulseValue: String
)

data class DailyPulseDto (
    var createdDate: String,
    var pulseList: List<Pulse>
)
