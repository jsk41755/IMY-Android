package com.cbnu_voice.cbnu_imy.view

import android.media.SoundPool
import android.os.Bundle
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cbnu_voice.cbnu_imy.Data.Message
import com.cbnu_voice.cbnu_imy.Dto.CorpusDto
import com.cbnu_voice.cbnu_imy.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class chatFragment : Fragment() {
    private val TAG = "MainActivity"
    private lateinit var speechRecognizer: SpeechRecognizer
    private var textToSpeech: TextToSpeech? = null

    lateinit var soundPool: SoundPool
    private var RefuseSoundList: List<Int>?  = null
    private var TreSoundList: List<Int>?  = null
    private var TTreSoundList: List<Int>?  = null

    //You can ignore this messageList if you're coming from the tutorial,
    // it was used only for my personal debugging
    var messagesList = mutableListOf<Message>()

    private lateinit var adapter: MessagingAdapter
    //private val botList = listOf("상우야", "정주야", "승규야")
    private var corpuslist : List<CorpusDto> = listOf() //corpus 데이터 받을 리스트 변수
    private lateinit var stage : String      //register로 부터 입력받은 우울증 단계
    private var chatresponse=""   //ai chatbot 답변

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

}