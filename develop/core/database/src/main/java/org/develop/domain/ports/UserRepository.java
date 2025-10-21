package org.develop.domain.ports;

import java.util.ArrayList;

import org.develop.domain.model.User;

/*
    -Interface for bruker-lister-
    Påkrevde metoder er agt opp for å hente og lagre brukere 
*/
public interface UserRepository{
    ArrayList<User> findAllUsers();
    boolean saveUser(Object command);
}