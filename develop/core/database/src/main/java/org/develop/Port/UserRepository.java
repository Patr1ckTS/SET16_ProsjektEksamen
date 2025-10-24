package org.develop.Port;

import java.util.ArrayList;

import org.develop.domain.User;
import org.develop.domain.CreateUser;

public interface UserRepository {
    ArrayList<User> findAllUsers();
    boolean saveUser(CreateUser newUser);
}
