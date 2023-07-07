package com.cbnu_voice.cbnu_imy.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_history")
data class ChatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val message: String,
    val timeStamp: String,
    val isLiked: Boolean
)

