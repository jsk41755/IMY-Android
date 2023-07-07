package com.cbnu_voice.cbnu_imy.view

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cbnu_voice.cbnu_imy.Data.Message
import com.cbnu_voice.cbnu_imy.Data.MessageEntity
import com.cbnu_voice.cbnu_imy.R
import com.cbnu_voice.cbnu_imy.Utils.Constants.RECEIVE_ID
import com.cbnu_voice.cbnu_imy.Utils.Constants.SEND_ID
import kotlinx.android.synthetic.main.message_item.view.*
import java.time.LocalDateTime
import java.util.ArrayList

class MessagingAdapter : RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>() {

    var messagesList = mutableListOf<Message>()
    var onBotLikeClickListener: OnBotLikeClickListener? = null

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMessage: TextView = itemView.findViewById(R.id.tv_message)
        val tvBotMessage: TextView = itemView.findViewById(R.id.tv_bot_message)
        val botClickLayout: LinearLayout = itemView.findViewById(R.id.botClickLayout)
        val botLikeButton: AppCompatImageButton = itemView.findViewById(R.id.botLike)
        var isBotClickLayoutVisible: Boolean = false
        var isBotLikeButtonClicked: Boolean = false
        var currentMessage: Message? = null

        init {
            itemView.tv_message.setOnClickListener(null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    fun setMessages(messages: MutableList<Message>) {
        messagesList = messages
        notifyDataSetChanged()
    }

    fun getMessages(): List<Message> {
        return messagesList
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messagesList[position]

        when (currentMessage.id) {
            SEND_ID -> {
                holder.tvMessage.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.tvBotMessage.visibility = View.GONE
            }
            RECEIVE_ID -> {
                holder.tvBotMessage.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.tvMessage.visibility = View.GONE
            }
        }

        holder.itemView.setOnClickListener {
            holder.isBotClickLayoutVisible = !holder.isBotClickLayoutVisible
            val botClickLayoutVisibility = if (holder.isBotClickLayoutVisible) View.VISIBLE else View.GONE
            holder.botClickLayout.visibility = botClickLayoutVisibility
        }

        holder.botLikeButton.setOnClickListener {
            holder.isBotLikeButtonClicked = !holder.isBotLikeButtonClicked
            val colorResId = if (holder.isBotLikeButtonClicked) R.color.bot_like_button_clicked else R.color.bot_like_button_default
            val color = ContextCompat.getColor(holder.itemView.context, colorResId)
            holder.botLikeButton.backgroundTintList = ColorStateList.valueOf(color)

            holder.currentMessage?.let { message ->
                // 클릭된 아이템의 데이터를 변경하고 업데이트합니다.
                message.isLiked = holder.isBotLikeButtonClicked
                notifyDataSetChanged()
            }

            // 클릭된 아이템의 데이터를 Local DB에 저장 또는 삭제합니다.
            onBotLikeClickListener?.let { listener ->
                val messageEntity = MessageEntity(
                    id = position.toLong(),
                    message = currentMessage.message,
                    timeStamp = LocalDateTime.now().toString(),
                    isLiked = holder.isBotLikeButtonClicked
                )
                if (holder.isBotLikeButtonClicked) {
                    listener.onLikeClicked(messageEntity)
                } else {
                    listener.onUnlikeClicked(messageEntity)
                }
            }
        }
    }

    fun insertMessage(message: Message) {
        this.messagesList.add(message)
        notifyItemInserted(messagesList.size)
    }

    interface OnBotLikeClickListener {
        fun onLikeClicked(messageEntity: MessageEntity)
        fun onUnlikeClicked(messageEntity: MessageEntity)
    }
}

