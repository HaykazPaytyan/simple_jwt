package org.energize.service;

import org.energize.domain.User;
import org.energize.interfaces.UserDAO;
import org.energize.utility.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserService implements UserDAO {

    @Override
    public User getById(long id) {

        User user = null;
        Session session = HibernateUtil.getSession();

        try {
            user = session.get(User.class, id);
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAll() {

        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from users");
        List<User> users =  query.list();
        session.close();

        return users;
    }

    @Override
    public boolean create(User user) {

        boolean successful = false;

        Session session = HibernateUtil.getSession();
        Transaction transaction = null;

        try{
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            successful = true;
        }catch (HibernateException e){
            transaction.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

        return successful;
    }

    @Override
    public boolean edit(User user, long id) {

        boolean successful = false;

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            User user1 = session.find(User.class, id);
            user1.setFirst_name(user.getFirst_name());
            user1.setLast_name(user.getLast_name());
            user1.setEmail(user.getEmail());
            user1.setPassword(user.getPassword());
            session.getTransaction().commit();
            successful = true;
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return successful;
    }

    @Override
    public boolean delete(long id) {

        boolean successful = false;

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            User user = session.find(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            successful = true;
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return successful;
    }
}
