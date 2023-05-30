package com.cbnu_voice.cbnu_imy

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.cbnu_voice.cbnu_imy.view.*
import com.cbnu_voice.cbnu_imy.databinding.FragActivityBinding
import com.cbnu_voice.cbnu_imy.viewmodel.MainViewModel
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import java.nio.charset.StandardCharsets
import java.util.HashSet

class FragmentActivity : AppCompatActivity(), CoroutineScope by MainScope(),
    DataClient.OnDataChangedListener,
    MessageClient.OnMessageReceivedListener,
    CapabilityClient.OnCapabilityChangedListener{
    private lateinit var binding: FragActivityBinding
    private lateinit var toolbar: Toolbar

    var activityContext: Context? = null
    private val wearableAppCheckPayload = "AppOpenWearable"
    private val wearableAppCheckPayloadReturnACK = "AppOpenWearableACK"
    private var wearableDeviceConnected: Boolean = false

    private var currentAckFromWearForAppOpenCheck: String? = null
    private val APP_OPEN_WEARABLE_PAYLOAD_PATH = "/APP_OPEN_WEARABLE_PAYLOAD"

    private val MESSAGE_ITEM_RECEIVED_PATH: String = "/message-item-received"

    private val TAG_GET_NODES: String = "getnodes1"
    private val TAG_MESSAGE_RECEIVED: String = "receive1"

    private var messageEvent: MessageEvent? = null
    private var wearableNodeUri: String? = null

    private var bpmPrint :String? = null

    private lateinit var sharedViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        binding.bottomNav.selectedItemId = R.id.menu_home

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_chat -> replaceFragment(chatFragment())
                R.id.menu_record -> replaceFragment(recordFragment())
                R.id.menu_home -> replaceFragment(HomeFragment())
                R.id.menu_favorite -> replaceFragment(favoriteFragment())
                R.id.menu_setting -> replaceFragment(settingFragment())

                else -> {

                }
            }
            true
        }


        activityContext = this
        wearableDeviceConnected = false

        sharedViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        sharedViewModel.bpmStack.observe(this) {
            if (sharedViewModel.bpmStack.value == 5) {
                AlertDialog.Builder(this)
                    .setTitle("잠깐 운동중이신가요??")
                    .setMessage("저와 이야기를 나누고 싶으면 말씀하세요!")
                    .setPositiveButton("네"
                    ) { _, _ -> binding.bottomNav.selectedItemId = R.id.menu_chat }
                    .setNegativeButton("아니오"
                    ) { _, _ -> }
                    .create()
                    .show()

            }
        }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
        fragmentTransaction.commit()
    }

    @SuppressLint("SetTextI18n")
    private fun initialiseDevicePairing(tempAct: Activity) {
        //Coroutine
        launch(Dispatchers.Default) {
            var getNodesResBool: BooleanArray? = null

            try {
                getNodesResBool =
                    getNodes(tempAct.applicationContext)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            //UI Thread
            withContext(Dispatchers.Main) {
                if (getNodesResBool!![0]) {
                    //if message Acknowlegement Received
                    if (getNodesResBool[1]) {
                        Toast.makeText(
                            activityContext,
                            "스마트워치와 연동 되었습니다.",
                            Toast.LENGTH_SHORT
                        ).show()

                        wearableDeviceConnected = true
                    } else {
                        Toast.makeText(
                            activityContext,
                            "스마트워치부터 실행하고 다시 실행해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                        wearableDeviceConnected = false
                    }
                } else {
                    Toast.makeText(
                        activityContext,
                        "스마트워치 연동 실패",
                        Toast.LENGTH_SHORT
                    ).show()
                    wearableDeviceConnected = false
                }
            }
        }
    }


    private fun getNodes(context: Context): BooleanArray {
        val nodeResults = HashSet<String>()
        val resBool = BooleanArray(2)
        resBool[0] = false //nodePresent
        resBool[1] = false //wearableReturnAckReceived
        val nodeListTask =
            Wearable.getNodeClient(context).connectedNodes
        try {
            // Block on a task and get the result synchronously (because this is on a background thread).
            val nodes =
                Tasks.await(
                    nodeListTask
                )
            Log.e(TAG_GET_NODES, "Task fetched nodes")
            for (node in nodes) {
                Log.e(TAG_GET_NODES, "inside loop")
                nodeResults.add(node.id)
                try {
                    val nodeId = node.id
                    // Set the data of the message to be the bytes of the Uri.
                    val payload: ByteArray = wearableAppCheckPayload.toByteArray()
                    // Send the rpc
                    // Instantiates clients without member variables, as clients are inexpensive to
                    // create. (They are cached and shared between GoogleApi instances.)
                    val sendMessageTask =
                        Wearable.getMessageClient(context)
                            .sendMessage(nodeId, APP_OPEN_WEARABLE_PAYLOAD_PATH, payload)
                    try {
                        // Block on a task and get the result synchronously (because this is on a background thread).
                        val result = Tasks.await(sendMessageTask)
                        Log.d(TAG_GET_NODES, "send message result : $result")
                        resBool[0] = true
                        //Wait for 1000 ms/1 sec for the acknowledgement message
                        //Wait 1
                        if (currentAckFromWearForAppOpenCheck != wearableAppCheckPayloadReturnACK) {
                            Thread.sleep(100)
                            Log.d(TAG_GET_NODES, "ACK thread sleep 1")
                        }
                        if (currentAckFromWearForAppOpenCheck == wearableAppCheckPayloadReturnACK) {
                            resBool[1] = true
                            return resBool
                        }
                        //Wait 2
                        if (currentAckFromWearForAppOpenCheck != wearableAppCheckPayloadReturnACK) {
                            Thread.sleep(150)
                            Log.d(TAG_GET_NODES, "ACK thread sleep 2")
                        }
                        if (currentAckFromWearForAppOpenCheck == wearableAppCheckPayloadReturnACK) {
                            resBool[1] = true
                            return resBool
                        }
                        //Wait 3
                        if (currentAckFromWearForAppOpenCheck != wearableAppCheckPayloadReturnACK) {
                            Thread.sleep(200)
                            Log.d(TAG_GET_NODES, "ACK thread sleep 3")
                        }
                        if (currentAckFromWearForAppOpenCheck == wearableAppCheckPayloadReturnACK) {
                            resBool[1] = true
                            return resBool
                        }
                        //Wait 4
                        if (currentAckFromWearForAppOpenCheck != wearableAppCheckPayloadReturnACK) {
                            Thread.sleep(250)
                            Log.d(TAG_GET_NODES, "ACK thread sleep 4")
                        }
                        if (currentAckFromWearForAppOpenCheck == wearableAppCheckPayloadReturnACK) {
                            resBool[1] = true
                            return resBool
                        }
                        //Wait 5
                        if (currentAckFromWearForAppOpenCheck != wearableAppCheckPayloadReturnACK) {
                            Thread.sleep(350)
                            Log.d(TAG_GET_NODES, "ACK thread sleep 5")
                        }
                        if (currentAckFromWearForAppOpenCheck == wearableAppCheckPayloadReturnACK) {
                            resBool[1] = true
                            return resBool
                        }
                        resBool[1] = false
                        Log.d(
                            TAG_GET_NODES,
                            "ACK thread timeout, no message received from the wearable "
                        )
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                } catch (e1: Exception) {
                    Log.d(TAG_GET_NODES, "send message exception")
                    e1.printStackTrace()
                }
            } //end of for loop
        } catch (exception: Exception) {
            Log.e(TAG_GET_NODES, "Task failed: $exception")
            exception.printStackTrace()
        }
        return resBool
    }



    override fun onDataChanged(p0: DataEventBuffer) {
    }


    override fun onCapabilityChanged(p0: CapabilityInfo) {
    }


    override fun onPause() {
        super.onPause()
        try {
            Wearable.getDataClient(activityContext!!).removeListener(this)
            Wearable.getMessageClient(activityContext!!).removeListener(this)
            Wearable.getCapabilityClient(activityContext!!).removeListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onMessageReceived(p0: MessageEvent) {
        var beforeBpm = StringBuilder()

        try {
            val s =
                String(p0.data, StandardCharsets.UTF_8)

            val messageEventPath: String = p0.path
            Log.d(
                TAG_MESSAGE_RECEIVED,
                "onMessageReceived() Received a message from watch:"
                        + p0.requestId
                        + " "
                        + messageEventPath
                        + " "
                        + s
            )
            if (messageEventPath == APP_OPEN_WEARABLE_PAYLOAD_PATH) {
                currentAckFromWearForAppOpenCheck = s
                Log.d(
                    TAG_MESSAGE_RECEIVED,
                    "Received acknowledgement message that app is open in wear"
                )
                messageEvent = p0
                wearableNodeUri = p0.sourceNodeId

                sharedViewModel.setBpm(s)


            } else if (messageEventPath.isNotEmpty() && messageEventPath == MESSAGE_ITEM_RECEIVED_PATH) {

                try {
                    beforeBpm.append("방금전, ")
                    beforeBpm.append(bpmPrint)
                    beforeBpm.append("BPM")

                    if(s.length == 2){
                        bpmPrint = s.substring(0 until 2)
                    }
                    else{
                        bpmPrint = s.substring(0 until 3)
                    }


                    /*if (s.toInt()/100 == 0){
                        bpmPrint = s.substring(0 until 2)
                    }
                    else{
                        bpmPrint = s.substring(0 until 3)
                    }*/

                    beforeBpm.setLength(0)
                    sharedViewModel.setBpm(bpmPrint)
                    sharedViewModel.bpmStack(1)

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("receive1", "Handled")
        }
    }


    override fun onResume() {
        super.onResume()
        try {
            if (!wearableDeviceConnected) {
                val tempAct: Activity = activityContext as FragmentActivity
                //Couroutine
                initialiseDevicePairing(tempAct)
            }
            Wearable.getDataClient(activityContext!!).addListener(this)
            Wearable.getMessageClient(activityContext!!).addListener(this)
            Wearable.getCapabilityClient(activityContext!!)
                .addListener(this, Uri.parse("wear://"), CapabilityClient.FILTER_REACHABLE)
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

}