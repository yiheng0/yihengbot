package moe.yiheng.service

import moe.yiheng.domain.Prpr

interface PrprService {
    fun findByUsernameAndChatId(username: String, chatId: Long): Prpr?
    fun add(prpr: Prpr)
    fun update(prpr: Prpr)
    fun sortByPrprStat(chatId: Long):List<Prpr>
}
