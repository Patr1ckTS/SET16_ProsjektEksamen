package org.develop.service;

import java.util.ArrayList;

import org.develop.Port.UserRepository;
import org.develop.domain.CreateUser;
import org.develop.domain.User;
import org.develop.useCases.NewUserUseCases;
import org.develop.useCases.UserUseCases;
import org.mindrot.jbcrypt.BCrypt;

// -Service for instansiering av klasser mot bruk av data-
// Håndterer kall til UseCases og formatering av data for 
// presentasjon i samhandling

public class UserService {
    private static UserRepository userRepository;
    private static UserUseCases userUseCases;
    private static NewUserUseCases newUserUseCases;
    private static final UserListFormatter formatter = new UserListFormatter();
    
    public static void initialize(UserRepository repo) {
        userRepository = repo;
        userUseCases = new UserUseCases(userRepository);
        newUserUseCases = new NewUserUseCases(userRepository);
    }

    public static String getUserFullname() {
        if (userUseCases == null) {
            return "<li>Service ikke initialiseret</li>";
        }
        
        try {
            ArrayList<User> users = userUseCases.execute();
            return formatter.formatUserList(users);
        } catch (Exception e) {
            return "<li>Feil ved henting av brukere: " + e.getMessage() + "</li>";
        }
    }

    public static boolean loginUser(String email, String password) {
        if (userUseCases == null) {
            System.err.println("UserService ikke initialisert");
            return false;
        }
        
        try {
            ArrayList<User> users = userUseCases.execute();
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
    
    public static String registerUser(String firstName, String lastName, String email, String phonenumber, String password) {
        if (newUserUseCases == null) {
            return "Service ikke initialisert";
        }
        
        if (emailExists(email)) {
            return "E-post allerede i bruk";
        }

        try {
            CreateUser newUser = new CreateUser(firstName, lastName, email, phonenumber, password);
            boolean success = newUserUseCases.execute(newUser);
            return success ? null : "Feil ved opprettelse av bruker";
        } catch (Exception e) {
            return "Feil ved registrering: " + e.getMessage();
        }
    }

    private static boolean emailExists(String email) {
        if (userUseCases == null) {
            return false;
        }
        
        try {
            ArrayList<User> users = userUseCases.execute();
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
    
    public static String getNameByEmail(String email) {
        if (userUseCases == null) {
            return null;
        }
        
        try {
            ArrayList<User> users = userUseCases.execute();
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
