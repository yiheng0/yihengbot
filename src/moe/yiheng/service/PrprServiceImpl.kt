package moe.yiheng.service

import moe.yiheng.dao.PrprDao
import moe.yiheng.dao.PrprDaoImpl
import moe.yiheng.domain.Prpr

class PrprServiceImpl:PrprService {
    private val dao:PrprDao = PrprDaoImpl()

    override fun add(prpr: Prpr) {
        dao.add(prpr)
    }

    override fun findByUsernameAndChatId(username: String, chatId: Long): Prpr? {
        return dao.findByUsernameAndChatId(username, chatId)
    }

    override fun update(prpr: Prpr) {
        dao.update(prpr)

    }
}