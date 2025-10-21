package org.develop.useCases;

import java.util.ArrayList;

import org.develop.domain.model.User;
import org.develop.domain.ports.UserRepository;

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
 