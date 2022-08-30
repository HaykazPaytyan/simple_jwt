package org.energize.service;

import org.energize.domain.User;
import org.energize.interfaces.UserDAO;



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
        return false;
    }

    @Override
    public boolean edit(User user, long id) {
        return false;
    }

    @Override
    public boolean delete(long id) { return false; }
}
