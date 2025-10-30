package org.develop.useCases;

import java.util.ArrayList;

import org.develop.Port.UserPort;
import org.develop.domain.User;

public class UserUseCases {
    private final UserPort userRepository;

    public UserUseCases(UserPort userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<User> arrayListOfRequestedUsers(){
        return new ArrayList<>(userRepository.findAllUsers());
    }
}