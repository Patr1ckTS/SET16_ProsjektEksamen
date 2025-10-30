package org.develop.useCases;

import org.develop.Port.UserPort;
import org.develop.domain.CreateUser;

// -useCases knyttet til samling og opprettelse av nye brukere i systemet-
// Validerer innlagte verdier før lagring i en ArrayList her må det gjøres
// endringer til database lagring på sikt

public class NewUserUseCases {
    private final UserPort userRepository;

    public NewUserUseCases(UserPort userRepository) {
        this.userRepository = userRepository;
    }

// Sjekker at innlagte verdier ikke er tomme før lagring
    public boolean newUserValueCheck(CreateUser newUser){
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
