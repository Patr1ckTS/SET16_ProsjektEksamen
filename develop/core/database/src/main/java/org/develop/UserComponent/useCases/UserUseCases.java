package org.develop.UserComponent.useCases;

import java.util.ArrayList;

import org.develop.UserComponent.Port.UserPort;
import org.develop.UserComponent.domain.User;

public class UserUseCases {
    private final UserPort userRepository;

    public UserUseCases(UserPort userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<User> arrayListOfRequestedUsers(){
        return new ArrayList<>(userRepository.findAllUsers());
    }
}