package moe.yiheng.dao

import moe.yiheng.domain.Chat
import moe.yiheng.utils.HibernateUtil
import org.hibernate.criterion.Restrictions

class ChatDaoImpl :ChatDao{
    override fun findByChatId(chatId: Long): Chat? {
        val session = HibernateUtil.getCurrentSession()
        val tx = session.beginTransaction()

        val c = session.createCriteria(Chat::class.java)
        c.add(Restrictions.eq("chatId",chatId))

        val list = c.list()
        tx.commit()
        return if (list.isEmpty()) null else list[0] as Chat
    }

    override fun add(chat: Chat) {
        val session = HibernateUtil.getCurrentSession()
        val tx = session.beginTransaction()

        session.save(chat)
        tx.commit()
    }

    override fun update(chat: Chat) {
        val session = HibernateUtil.getCurrentSession()
        val tx = session.beginTransaction()

        session.update(chat)
        tx.commit()
    }
}