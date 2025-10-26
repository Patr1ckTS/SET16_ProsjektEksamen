package org.develop.useCases;

import java.util.ArrayList;

import org.develop.Port.UserRepository;
import org.develop.domain.User;

public class UserUseCases {
    private final UserRepository userRepository;

    public UserUseCases(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<User> arrayListOfRequestedUsers(){
        return new ArrayList<>(userRepository.findAllUsers());
    }
}