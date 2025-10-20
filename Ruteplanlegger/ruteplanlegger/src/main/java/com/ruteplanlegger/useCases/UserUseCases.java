package com.ruteplanlegger.useCases;

import java.util.ArrayList;

import com.ruteplanlegger.domain.model.User;
import com.ruteplanlegger.domain.ports.UserRepository;

/*
    -Use case for å hente alle brukere-
    Inneholder metoden for å hente alle brukere fra databasen
*/

public class UserUseCases {
    private final UserRepository userRepository;

    public UserUseCases(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<User> execute(){
        return userRepository.findAllUsers();
    }
}
 