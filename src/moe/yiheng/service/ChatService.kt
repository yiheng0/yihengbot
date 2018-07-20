package moe.yiheng.service

import moe.yiheng.domain.Chat

interface ChatService {
    fun findByChatId(chatId: Long): Chat?
    fun add(chat: Chat)
    fun update(chat: Chat)
}