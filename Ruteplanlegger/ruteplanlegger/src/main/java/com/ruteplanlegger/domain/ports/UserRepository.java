package com.ruteplanlegger.domain.ports;

import java.util.ArrayList;

import com.ruteplanlegger.domain.model.User;

/*
    -Interface for bruker-lister-
    Påkrevde metoder er agt opp for å hente og lagre brukere 
*/
public interface UserRepository{
    ArrayList<User> findAllUsers();
    boolean saveUser(Object command);
}