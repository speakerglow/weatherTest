package com.example.weathertest.models

data class MessagesDto(
    val id: Int? = null,
    val chatId: Int? = null,
    val fromUserId: String? = null,
    val toUserId: String? = null,
    val content: String? = null,
    val date: String? = null,
    val chat: String? = null,
    val messageStatuses: Array<String>?,
    val deleted: Boolean? = null
)