package moe.yiheng.dao

import moe.yiheng.domain.Prpr
import moe.yiheng.utils.HibernateUtil
import org.hibernate.criterion.Restrictions

class PrprDaoImpl : PrprDao {
    override fun findByUsernameAndChatId(username:String,chatId:Long): Prpr? {
        val session = HibernateUtil.getCurrentSession()
        val tx = session.beginTransaction()

        val c = session.createCriteria(Prpr::class.java)
        c.add(Restrictions.eq("username",username.toLowerCase()))
        c.add(Restrictions.eq("chatId", chatId))
        val list = c.list()
        tx.commit()
        return if (list.isEmpty()) null else list[0] as Prpr
    }

    override fun add(prpr: Prpr) {
        val session = HibernateUtil.getCurrentSession()
        val tx = session.beginTransaction()

        session.save(prpr)
        tx.commit()

    }

    override fun update(prpr: Prpr) {
        val session = HibernateUtil.getCurrentSession()
        val tx = session.beginTransaction()

        session.update(prpr)
        tx.commit()

    }
}