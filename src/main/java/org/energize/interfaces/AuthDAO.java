package org.energize.interfaces;

import org.energize.domain.Credential;
import org.energize.domain.User;

public interface AuthDAO {
    public User attempt(Credential credential);
}
