package org.energize.utility;



import org.energize.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static HibernateUtil instance = new HibernateUtil();

    private SessionFactory sessionFactory;

    public static HibernateUtil getInstance() {
        return instance;
    }

    private HibernateUtil() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        configuration.addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static Session getSession() {
        Session session = getInstance().sessionFactory.openSession();
        return session;
    }
}
