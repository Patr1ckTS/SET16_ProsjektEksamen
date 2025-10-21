package com.database.domain.ports;

import java.util.ArrayList;

import com.database.domain.interfaces.CreateUser;
import com.database.domain.interfaces.User;

/*
    -Interface for bruker-lister-
    Påkrevde metoder er lagt opp for
    - Henting av brukere
    - Lagring av ny bruker 
*/
public interface UserRepository{
    ArrayList<User> findAllUsers();
    boolean saveUser(CreateUser newUser);
}