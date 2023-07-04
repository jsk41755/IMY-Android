package com.cbnu_voice.cbnu_imy.Dto.Pulse

import com.cbnu_voice.cbnu_imy.Data.Pulse
data class DailyPulseDto (
    var createdDate: String,
    var pulseList: List<Pulse>
)
