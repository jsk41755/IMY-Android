package com.cbnu_voice.cbnu_imy.Dto.Pulse

import com.cbnu_voice.cbnu_imy.Data.PulseData

data class PulseDto (
    var createdDate: String,
    var pulseList: List<PulseData>
)
