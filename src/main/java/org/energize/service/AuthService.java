package org.energize.service;

import org.energize.domain.Credential;
import org.energize.domain.User;
import org.energize.interfaces.AuthDAO;
import org.energize.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class AuthService implements AuthDAO {

    @Override
    public User attempt(Credential credential) {
        Session session = HibernateUtil.getSession();
        String hql = "FROM User U WHERE U.email=:credential_email AND  U.password=:credential_password";
        Query query = session.createQuery(hql,User.class);
        query.setParameter("credential_email",credential.getEmail());
        query.setParameter("credential_password",credential.getPassword());
        User user = (User) query.getSingleResult();
        session.close();
        return user;
    }




}
