package com.cbnu_voice.cbnu_imy.Utils

//문자열이 제이슨 형태인지, 제이슨 배열 형태인지
fun String?.isJsonObject(): Boolean {
    return this?.startsWith("{") == true && this.endsWith("}")
}

//문자열이 제이슨 배열인지
fun String?.isJasonArray():Boolean{
    return this?.startsWith("[") == true && this.endsWith("]")
}