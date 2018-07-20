package moe.yiheng.dao

import moe.yiheng.domain.Chat

interface ChatDao {
    fun findByChatId(chatId: Long): Chat?
    fun add(chat: Chat)
    fun update(chat: Chat)
}