package moe.yiheng.service

import moe.yiheng.dao.ChatDao
import moe.yiheng.dao.ChatDaoImpl
import moe.yiheng.domain.Chat

class ChatServiceImpl:ChatService {
    val dao:ChatDao = ChatDaoImpl()

    override fun findByChatId(chatId: Long): Chat? {
        return dao.findByChatId(chatId)
    }

    override fun add(chat: Chat) {
        return dao.add(chat)
    }

    override fun update(chat: Chat) {
        return dao.update(chat)
    }
}