package com.cbnu_voice.cbnu_imy.Utils

object Constans {
    const val TAG : String = "로그"
}

object API{
    const val Base_URL : String = "http://49.143.65.133:15606"
    const val GET_PULSE_AVG : String = "/pulse/daily/avg/all"
}

enum class RESPONSE_STATE {
    OKAY,
    FAIL
}