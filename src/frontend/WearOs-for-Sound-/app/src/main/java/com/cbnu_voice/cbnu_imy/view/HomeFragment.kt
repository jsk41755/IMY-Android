package com.cbnu_voice.cbnu_imy.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cbnu_voice.cbnu_imy.FragmentActivity
import com.cbnu_voice.cbnu_imy.R
import com.cbnu_voice.cbnu_imy.viewmodel.MainViewModel
import com.cbnu_voice.cbnu_imy.databinding.FragmentHomeBinding

class HomeFragment : Fragment(){

    private var binding: FragmentHomeBinding? = null

    private lateinit var sharedViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.data.observe(viewLifecycleOwner, Observer {
            // 데이터 업데이트 시 처리할 작업
            binding?.messagelogTextView2!!.text = sharedViewModel.data.value
            sharedViewModel.data.value?.let { bpmText(it) }
        })
    }

    private fun bpmText(bpm: String){
        if (bpm!!.toInt() > 90) {
            binding?.bpmtxt!!.text = "다소 불안정 합니다."
        } else if (bpm!!.toInt() in 60..89) {
            binding?.bpmtxt!!.text = "현재 매우 안정적 입니다."
        } else {
            binding?.bpmtxt!!.text = "현재 맥박이 매우 낮습니다."
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}