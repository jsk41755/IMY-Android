package com.cbnu_voice.cbnu_imy.view


import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cbnu_voice.cbnu_imy.Data.Message
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Debug
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cbnu_voice.cbnu_imy.Api.RetrofitBuilder
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.cbnu_voice.cbnu_imy.Api.Clova.fetchAudioUrl
import com.cbnu_voice.cbnu_imy.App
import com.cbnu_voice.cbnu_imy.BuildConfig
import com.cbnu_voice.cbnu_imy.ChatDatabase
import com.cbnu_voice.cbnu_imy.Dao.ChatMessageDao
import com.cbnu_voice.cbnu_imy.Data.ChatEntity
import com.cbnu_voice.cbnu_imy.DataStoreModule
import com.cbnu_voice.cbnu_imy.R
import com.cbnu_voice.cbnu_imy.Utils.Constants.OPEN_GOOGLE
import com.cbnu_voice.cbnu_imy.Utils.Constants.OPEN_SEARCH
import com.cbnu_voice.cbnu_imy.Utils.Constants.RECEIVE_ID
import com.cbnu_voice.cbnu_imy.Utils.Constants.SEND_ID
import com.cbnu_voice.cbnu_imy.Utils.Time
import com.cbnu_voice.cbnu_imy.databinding.FragmentChatBinding
import com.cbnu_voice.cbnu_imy.viewmodel.MainViewModel
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import java.io.File
import java.util.*


class chatFragment : Fragment() {
    private lateinit var speechRecognizer: SpeechRecognizer
    private var textToSpeech: TextToSpeech? = null

    private lateinit var sharedViewModel: MainViewModel

    var messagesList = mutableListOf<Message>()

    private lateinit var adapter: MessagingAdapter
    private var chatresponse=""   //ai chatbot 답변

    private var binding: FragmentChatBinding? = null

    private lateinit var player: ExoPlayer

    private lateinit var speaker : String
    private var selectNum : Int = 2

    private lateinit var chatMessageDao: ChatMessageDao
    private lateinit var chatMessageDatabase: ChatDatabase

    private lateinit var app: App
    private lateinit var datastore: DataStoreModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermission()

        sharedViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app = requireContext().applicationContext as App
        datastore = app.datastore

        Log.d("countNum", datastore.chatId.value.toString())

        chatMessageDatabase = ChatDatabase.getDatabase(requireContext())
        chatMessageDao = chatMessageDatabase.chatMessageDao()

        /*CoroutineScope(Dispatchers.IO).launch {
            val messages = messageDao.getAllMessages()
            for (message in messages) {
                Log.d("Message", "ID: ${message.id}, Message: ${message.message}")
            }
        }*/

        CoroutineScope(Dispatchers.Main).launch {
            speaker = datastore.text.first()
            selectNum = datastore.Num.first()
        }

        recyclerView()
        clickEvents()
        // 데이터베이스에서 아이템 로드하여 Adapter에 설정
        loadMessages()

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
       // intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)    // 여분의 키
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")         // 언어 설정

        val builder = ExoPlayer.Builder(requireContext(), DefaultRenderersFactory(requireContext()))
        player = builder.setTrackSelector(DefaultTrackSelector(requireContext()))
            .build()

        val coroutineScope = CoroutineScope(Dispatchers.IO)

        if(datastore.chatId.value == 0){
            coroutineScope.launch {
                val helloBotMessage = resources.getString(R.string.helloBotMessage)
                val timeStamp = Time.timeStamp()
                customBotMessage(helloBotMessage)
                delay(1000)
                withContext(Dispatchers.Main) {
                    chatMessageDao.insertMessage(ChatEntity(datastore.chatId.value, helloBotMessage, timeStamp, isLiked = false))
                    datastore.countNum()
                    rv_messages.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }

        sharedViewModel.data.observe(viewLifecycleOwner,  Observer {
            // 데이터 업데이트 시 처리할 작업
            binding?.bpmChatTxt!!.text = sharedViewModel.data.value}
        )

        binding?.btnSpeech!!.setOnClickListener {
            // 새 SpeechRecognizer 를 만드는 팩토리 메서드
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())
            speechRecognizer.setRecognitionListener(recognitionListener)    // 리스너 설정
            speechRecognizer.startListening(intent)                         // 듣기 시작
        }

        adapter.onBotLikeClickListener = object : MessagingAdapter.OnBotLikeClickListener{
            override fun onLikeClicked(chatEntity: ChatEntity) {
                CoroutineScope(Dispatchers.IO).launch {
                    //messageDao.insertMessage(messageEntity)
                    chatMessageDao.updateMessage(chatEntity)
                    Log.d("MessageDao", "Message inserted: ${chatEntity.id}, ${chatEntity.message}, ${chatEntity.isLiked}")
                }
            }

            override fun onUnlikeClicked(chatEntity: ChatEntity) {
                CoroutineScope(Dispatchers.IO).launch {
                    //messageDao.deleteMessage(messageEntity)
                    chatMessageDao.deleteMessage(chatEntity)
                    Log.d("MessageDao", "Message updated: ${chatEntity.id}, ${chatEntity.message}, ${chatEntity.isLiked}")
                }
            }
        }
    }
    private fun loadMessages() {
        lifecycleScope.launch {
            val chatEntities = withContext(Dispatchers.IO) {
                chatMessageDao.getAllMessages()
            }
            val messages = chatEntities.map { chatEntity ->
                Message(
                    chatEntity.message,
                    if (chatEntity.id % 2 != 0) RECEIVE_ID else SEND_ID,
                    chatEntity.timeStamp,
                    chatEntity.isLiked
                )
            }
            adapter.setMessages(messages.toMutableList())
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentChatBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    // 권한 설정 메소드
    private fun requestPermission() {
        // 버전 체크, 권한 허용했는지 체크
        if (Build.VERSION.SDK_INT >= 23 &&
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(context as Activity,
                arrayOf(Manifest.permission.RECORD_AUDIO), 0)
        }
    }

    // 리스너 설정
    private val recognitionListener: RecognitionListener = object : RecognitionListener {
        // 말하기 시작할 준비가되면 호출
        override fun onReadyForSpeech(params: Bundle) {
            Toast.makeText(requireContext(), "음성인식 시작", Toast.LENGTH_SHORT).show()
            binding?.tvState!!.text = "이제 말씀하세요!"
        }
        // 말하기 시작했을 때 호출
        override fun onBeginningOfSpeech() {
            binding?.tvState!!.text = "잘 듣고 있어요."
        }
        // 입력받는 소리의 크기를 알려줌
        override fun onRmsChanged(rmsdB: Float) {}
        // 말을 시작하고 인식이 된 단어를 buffer에 담음
        override fun onBufferReceived(buffer: ByteArray) {}
        // 말하기를 중지하면 호출
        override fun onEndOfSpeech() {
            binding?.tvState!!.text = "끝!"
        }
        // 오류 발생했을 때 호출
        override fun onError(error: Int) {
            val message = when (error) {
                SpeechRecognizer.ERROR_AUDIO -> "오디오 에러"
                SpeechRecognizer.ERROR_CLIENT -> "클라이언트 에러"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "퍼미션 없음"
                SpeechRecognizer.ERROR_NETWORK -> "네트워크 에러"
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "네트웍 타임아웃"
                SpeechRecognizer.ERROR_NO_MATCH -> "찾을 수 없음"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RECOGNIZER 가 바쁨"
                SpeechRecognizer.ERROR_SERVER -> "서버가 이상함"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "말하는 시간초과"
                else -> "알 수 없는 오류임"
            }
            binding?.tvState!!.text = "에러 발생: $message"
        }
        // 인식 결과가 준비되면 호출
        override fun onResults(results: Bundle) {
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줌
            val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            for (i in matches!!.indices) binding?.etMessage!!.setText(matches[i])
            sendMessage()

        }
        // 부분 인식 결과를 사용할 수 있을 때 호출
        override fun onPartialResults(partialResults: Bundle) {}
        // 향후 이벤트를 추가하기 위해 예약
        override fun onEvent(eventType: Int, params: Bundle) {}
    }

    private fun clickEvents() {
        //Send a message
        btn_send.setOnClickListener {
            sendMessage()
        }
        //Scroll back to correct position when user clicks on text view
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onStart() {
        super.onStart()

        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun startAudioStreaming(s: String) {
        val url = BuildConfig.TTS_API_KEY + s

        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            // ExoPlayer.Builder를 사용하여 ExoPlayer 인스턴스 생성
            val builder = ExoPlayer.Builder(requireContext(), DefaultRenderersFactory(requireContext()))
            player = builder.setTrackSelector(DefaultTrackSelector(requireContext()))
                .build()

            val mediaItem = MediaItem.fromUri(Uri.parse(url))

            val mediaSource = ProgressiveMediaSource.Factory(
                DefaultDataSourceFactory(requireContext(), "exoplayer-sample")
            ).createMediaSource(mediaItem)

            withContext(Dispatchers.Main){
                // ExoPlayer에 MediaSource 설정
                player.setMediaSource(mediaSource)
                // 재생 시작
                //player.playWhenReady = true
                player.prepare()
                player.play()
            }
        }
    }
    private fun naverClovaVoice(message: String) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            // Fetch audio URL using fetchAudioUrl function
            val audioUrl = fetchAudioUrl(requireContext(), message, speaker)
            var filePath: String? = null

            val mediaItem = MediaItem.fromUri(Uri.fromFile(File(audioUrl)))

            val mediaSource = ProgressiveMediaSource.Factory(
                DefaultDataSourceFactory(requireContext(), "exoplayer-sample")
            ).createMediaSource(mediaItem)

            withContext(Dispatchers.Main) {
                filePath = audioUrl

                player.setMediaSource(mediaSource)
                player.prepare()
                player.play()

                player.addListener(object : Player.Listener {
                    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                        if (playbackState == Player.STATE_ENDED) {
                            val outputFile = filePath?.let { File(it) }
                            val result = outputFile?.deleteRecursively()
                            if (result == true) {
                                println("Deletion succeeded.")
                            } else {
                                println("Deletion failed.")
                            }
                        }
                    }
                })
            }
        }
    }

    private fun sendMessage() {
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            //Adds it to our local list
            messagesList.add(Message(message, SEND_ID, timeStamp, isLiked=false))
            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp, isLiked=false))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    datastore.countNum()
                    chatMessageDao.insertMessage(ChatEntity(datastore.chatId.value, message, timeStamp, isLiked = false))
                }
            }
        }
    }
    private fun botResponse(message: String) {

        val timeStamp = Time.timeStamp()

        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            //Fake response delay
            //delay(1000)
            var response=""

            withContext(Dispatchers.Main) {

                //Gets the response(3 case)
                Chatbotlist(message);
                response=chatresponse;

                //Adds it to our local list

                //messagesList.add(Message(response, RECEIVE_ID, timeStamp))
                messagesList.add(Message(response, RECEIVE_ID, timeStamp, isLiked=false))

                //Inserts our message into the adapter
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp, isLiked=false))
                //adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))
                //Scrolls us to the position of the latest message
                rv_messages.scrollToPosition(adapter.itemCount - 1)

                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        datastore.countNum()
                        chatMessageDao.insertMessage(ChatEntity(datastore.chatId.value, response, timeStamp, isLiked = false))
                    }
                }

                //Starts Google
                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                }

                if(selectNum == 6){
                    startAudioStreaming(response)
                } else {
                    naverClovaVoice(response)
                }
            }
        }
    }

    private fun customBotMessage(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, RECEIVE_ID, timeStamp, isLiked=false))

                val rootView = requireView()
                rootView.post{
                    adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp, isLiked=false))
                }

                rv_messages.scrollToPosition(adapter.itemCount - 1)

                if(selectNum == 6){
                    startAudioStreaming(message)
                } else {
                    naverClovaVoice(message)
                }
            }
        }
    }

    private suspend fun Chatbotlist(s: String) {
        withContext(Dispatchers.IO) {
            runCatching {
                val startTime = System.currentTimeMillis()

                val retrofit = RetrofitBuilder.chatbotapi.getKobertResponse(s)
                val res = retrofit.execute().body()

                val endTime = System.currentTimeMillis()
                val duration = endTime - startTime
                Log.v("응답 속도", "${duration}")

                chatresponse= res!!.answer
            }.getOrDefault(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        player.release()
    }
}