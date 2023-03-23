package com.cbnu_voice.cbnu_imy

import androidx.fragment.app.Fragment
import com.google.android.gms.wearable.MessageEvent

class fragmentSet : Fragment() {
    private val TAG_MESSAGE_RECEIVED: String = "receive1"
    private val wearableAppCheckPayload = "AppOpenWearable"
    private val wearableAppCheckPayloadReturnACK = "AppOpenWearableACK"
    private var wearableDeviceConnected: Boolean = false

    private var currentAckFromWearForAppOpenCheck: String? = null
    private val APP_OPEN_WEARABLE_PAYLOAD_PATH = "/APP_OPEN_WEARABLE_PAYLOAD"

    private val MESSAGE_ITEM_RECEIVED_PATH: String = "/message-item-received"

    private val TAG_GET_NODES: String = "getnodes1"

    private var messageEvent: MessageEvent? = null
    private var wearableNodeUri: String? = null
    private var heartRateCount: Int=0

    private var bpmPrint :String? = null

}