package org.develop.UserComponent.Port;

import java.util.ArrayList;

import org.develop.UserComponent.domain.CreateUser;
import org.develop.UserComponent.domain.User;

public interface UserPort {
    ArrayList<User> findAllUsers();
    boolean saveUser(CreateUser newUser);
}