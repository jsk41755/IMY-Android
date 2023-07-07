package com.cbnu_voice.cbnu_imy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.cbnu_voice.cbnu_imy.AppDatabase
import com.cbnu_voice.cbnu_imy.Dao.MessageDao
import com.cbnu_voice.cbnu_imy.Data.MessageEntity
import com.cbnu_voice.cbnu_imy.R
import com.cbnu_voice.cbnu_imy.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.launch

class favoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding

    private lateinit var messageDao: MessageDao
    private lateinit var messageDatabase: AppDatabase

    private lateinit var favoriteMessages: List<MessageEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageDatabase = AppDatabase.getDatabase(requireContext())
        messageDao = messageDatabase.messageDao()

        loadFavoriteMessages()
        binding = FragmentFavoriteBinding.bind(view)
    }

    private fun loadFavoriteMessages() {
        val messageDao = AppDatabase.getDatabase(requireContext()).messageDao()

        lifecycleScope.launch {
            favoriteMessages = messageDao.getAllMessages()
            val likeMessageCount = favoriteMessages.size.toString()
            binding.likeMessageCount.text = "$likeMessageCount message"
        }
    }

}