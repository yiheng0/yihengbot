package moe.yiheng.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory factory;

    static {
        try {
            Configuration cfg = new Configuration();
            cfg.configure();
            factory = cfg.buildSessionFactory();
        } catch (ExceptionInInitializerError e) {
            throw new ExceptionInInitializerError("初始化失败 请检查hibernate配置文件");
        }
    }

    /**
     * 获取新的Session对象
     * @return session
     */
    public static Session openSession(){
        return factory.openSession();
    }

    /**
     * 获取当前线程的session
     * @return session
     */
    public static Session getCurrentSession() {
        return factory.getCurrentSession();
    }
}
