package org.develop.useCases;

import org.develop.domain.CreateUser;
import org.develop.Port.UserRepository;

public class NewUserUseCases {
    private final UserRepository userRepository;

    public NewUserUseCases(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean execute(CreateUser newUser){
        if (newUser.getFirstName() == null || newUser.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("Firstname cannot be null or empty");
        }
        if (newUser.getLastName() == null || newUser.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Lastname cannot be null or empty");
        }
        if (newUser.getEmail() == null || newUser.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (newUser.getPhoneNumber() == null || newUser.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (newUser.getPassword() == null || newUser.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return userRepository.saveUser(newUser);
    }
}
