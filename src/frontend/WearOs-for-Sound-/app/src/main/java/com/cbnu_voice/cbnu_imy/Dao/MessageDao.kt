package com.cbnu_voice.cbnu_imy.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cbnu_voice.cbnu_imy.Data.MessageEntity

@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessage(message: MessageEntity)

    @Delete
    suspend fun deleteMessage(message: MessageEntity)

    @Query("SELECT * FROM messages")
    suspend fun getAllMessages(): List<MessageEntity>
}
