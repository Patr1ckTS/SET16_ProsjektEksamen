package com.ruteplanlegger.domain.ports;

import java.util.ArrayList;

import com.ruteplanlegger.domain.model.User;

public interface UserRepository{
    ArrayList<User> findAllUsers();
    boolean saveUser(Object command);
}