package com.cbnu_voice.cbnu_imy.Dto.Pulse

import java.time.LocalDate
import java.time.LocalTime

data class Pulse (
    val createdTime: LocalTime,
    val pulseValue: String
)

data class DailyPulseDto (
    var createdDate: LocalDate,
    var pulseList: List<Pulse>
)
