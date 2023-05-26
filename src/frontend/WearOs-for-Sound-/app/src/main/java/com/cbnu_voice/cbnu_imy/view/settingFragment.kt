package com.cbnu_voice.cbnu_imy.view

import android.R
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.cbnu_voice.cbnu_imy.App
import com.cbnu_voice.cbnu_imy.Data.Voice
import com.cbnu_voice.cbnu_imy.databinding.FragmentSettingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class settingFragment : Fragment() {
    private var binding: FragmentSettingBinding? = null

    private lateinit var builder : AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val options = listOf(
            Voice("아라(여자)", "nmeow", 1),
            Voice("유나(여자)", "vyuna", 2),
            Voice("지훈(남자)", "nwoof", 3),
            Voice("동현(남자)", "vdonghyun", 4),
            Voice("혜리(여자)", "vhyeri", 5),
            Voice("정주(실제 남자)", "Speaker 6", 6)
        )

        val optionsAdapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_list_item_1,
            options.map {
                it.title
            }
        )

        builder = AlertDialog.Builder(requireContext())
        builder.setTitle("원하는 목소리를 선택해주세요.")
        builder.setAdapter(optionsAdapter) { _, position ->
            val selectedOption = options[position]
            lifecycleScope.launch {
                // DataStore에 값을 저장할 때
                saveSelectedVoice(selectedOption)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentSettingBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        binding!!.settingVoice.setOnClickListener {
            builder.show()
        }
    }

    private suspend fun saveSelectedVoice(voice: Voice) {
        CoroutineScope(Dispatchers.Main).launch {
            App.getInstance().getDataStore().setText(voice.speaker, voice.selectNum)
            Log.d("voice", voice.speaker)
        }
    }
}