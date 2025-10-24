package org.develop.useCases;

import java.util.ArrayList;

import org.develop.domain.User;
import org.develop.Port.UserRepository;

public class UserUseCases {
    private final UserRepository userRepository;

    public UserUseCases(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<User> execute(){
        return new ArrayList<>(userRepository.findAllUsers());
    }
}
