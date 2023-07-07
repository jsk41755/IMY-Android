package com.cbnu_voice.cbnu_imy.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val message: String,
    val timeStamp: String,
    val isLiked: Boolean
)
