package com.cbnu_voice.cbnu_imy.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cbnu_voice.cbnu_imy.Data.ChatEntity

@Dao
interface ChatMessageDao {
    @Insert
    suspend fun insertMessage(message: ChatEntity)

    @Update
    suspend fun updateMessage(message: ChatEntity)

    @Update
    suspend fun deleteMessage(message: ChatEntity)

    @Query("SELECT * FROM chat_history")
    suspend fun getAllMessages(): List<ChatEntity>

    @Query("SELECT * FROM chat_history WHERE isLiked")
    suspend fun getLikedMessages(): List<ChatEntity>
}
