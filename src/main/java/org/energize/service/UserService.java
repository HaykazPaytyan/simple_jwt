package org.energize.service;

import org.energize.domain.User;
import org.energize.interfaces.UserDAO;
import org.energize.utility.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

public class UserService implements UserDAO {

    @Override
    public User getById(long id) {
        return null;
    }

    @Override
    public List<User> getAll() { return null; }

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
        return false;
    }

    @Override
    public boolean delete(long id) { return false; }
}
