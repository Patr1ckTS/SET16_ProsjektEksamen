package com.database.useCases;

import com.database.domain.model.CreateUserCommand;
import com.database.domain.ports.UserRepository;

public class NewUserUseCases {
    private final UserRepository userRepository;

    public NewUserUseCases(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean execute(CreateUserCommand command){
        if (command.getFirstName() == null || command.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("Firstname cannot be null or empty");
        }
        if (command.getLastName() == null || command.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Lastname cannot be null or empty");
        }
        if (command.getEmail() == null || command.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (command.getPhoneNumber() == null || command.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (command.getPassword() == null || command.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return userRepository.saveUser(command);
    }
}
 