package moe.yiheng.dao

import moe.yiheng.domain.Prpr

interface PrprDao {
    fun findByUsernameAndChatId(username: String, chatId: Long): Prpr?
    fun add(prpr: Prpr)
    fun update(prpr: Prpr)
    fun sortByPrprStat(chatId: Long): List<Prpr>
}
