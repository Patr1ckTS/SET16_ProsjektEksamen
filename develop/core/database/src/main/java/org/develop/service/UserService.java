package org.develop.service;

import java.util.ArrayList;

import org.develop.Port.UserRepository;
import org.develop.domain.CreateUser;
import org.develop.domain.User;
import org.develop.useCases.NewUserUseCases;
import org.develop.useCases.UserUseCases;
import org.mindrot.jbcrypt.BCrypt;

// Service for håndtering av brukeroperasjoner
// Bruker Dependency Injection via constructor for UserRepository
// Håndterer kall til UseCases og formatering av data for presentasjon

public class UserService {
    private final UserUseCases userUseCases;
    private final NewUserUseCases newUserUseCases;
    private final UserListFormatter formatter;

    // Constructor Injection - følger Dependency Injection pattern
    public UserService(UserRepository userRepository) {
        this.userUseCases = new UserUseCases(userRepository);
        this.newUserUseCases = new NewUserUseCases(userRepository);
        this.formatter = new UserListFormatter();
    }

    public String getUserFullname() {
        try {
            ArrayList<User> users = userUseCases.arrayListOfRequestedUsers();
            return formatter.formatUserList(users);
        } catch (Exception e) {
            return "<li>Feil ved henting av brukere: " + e.getMessage() + "</li>";
        }
    }

    public boolean loginUser(String email, String password) {
        try {
            ArrayList<User> users = userUseCases.arrayListOfRequestedUsers();
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    return BCrypt.checkpw(password, user.getPassword());
                }
            }
        } catch (Exception e) {
            System.err.println("Feil ved innlogging: " + e.getMessage());
        }

        return false;
    }

    public String registerUser(String firstName, String lastName, String email, String phonenumber, String password) {
        if (emailExists(email)) {
            return "E-post allerede i bruk";
        }

        try {
            CreateUser newUser = new CreateUser(firstName, lastName, email, phonenumber, password);
            boolean success = newUserUseCases.newUserValueCheck(newUser);
            return success ? null : "Feil ved opprettelse av bruker";
        } catch (Exception e) {
            return "Feil ved registrering: " + e.getMessage();
        }
    }

    private boolean emailExists(String email) {
        try {
            ArrayList<User> users = userUseCases.arrayListOfRequestedUsers();
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Feil ved sjekking av epost: " + e.getMessage());
        }

        return false;
    }

    public String getNameByEmail(String email) {
        try {
            ArrayList<User> users = userUseCases.arrayListOfRequestedUsers();
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    return user.getFullName();
                }
            }
        } catch (Exception e) {
            System.err.println("Feil ved henting av navn: " + e.getMessage());
        }
        return null;
    }
}
