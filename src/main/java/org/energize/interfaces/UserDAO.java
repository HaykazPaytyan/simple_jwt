package org.energize.interfaces;

import org.energize.domain.User;

import java.util.List;

public interface UserDAO {

    User getById(long id);
    List<User> getAll();
    boolean create(User user);
    boolean edit(User user,long id);
    boolean delete(long id);
}
