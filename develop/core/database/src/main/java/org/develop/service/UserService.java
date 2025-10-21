package org.develop.service;

import java.util.ArrayList;

import org.develop.adapters.DatabaseUserRepository;
import org.develop.domain.DatabaseSetup;
import org.develop.domain.model.CreateUserCommand;
import org.develop.domain.model.User;
import org.develop.domain.ports.UserRepository;
import org.develop.useCases.NewUserUseCases;
import org.develop.useCases.UserUseCases;
import org.mindrot.jbcrypt.BCrypt;


public class UserService {
    private static UserRepository userRepository;
    private static UserUseCases userUseCases;
    private static NewUserUseCases newUserUseCases;
    
    public static void initialize(DatabaseSetup dbSetup) {
        
        DatabaseConnection databaseConnection = new DatabaseConnection(
            dbSetup.getDbUrl(), dbSetup.getDbUsername(), dbSetup.getDbPassword()
        );
        userRepository = new DatabaseUserRepository(databaseConnection);
        userUseCases = new UserUseCases(userRepository);
        newUserUseCases = new NewUserUseCases(userRepository);
    }
    
    public static String getUserFullname() {
        System.out.println("DEBUG: getUserFullname() called");
        if (userUseCases == null) {
            System.out.println("DEBUG: userUseCases is null - UserService not initialized");
            return "Error: UserService not initialized.";
        }
        
        try {
            System.out.println("DEBUG: Attempting to execute userUseCases");
            ArrayList<User> users = userUseCases.execute();
            System.out.println("DEBUG: Retrieved " + users.size() + " users from database");
            String result = formatUsersAsHtml(users);
            System.out.println("DEBUG: Formatted HTML result: " + result);
            return result;
        } catch (Exception e) {
            System.out.println("DEBUG: Exception in getUserFullname: " + e.getMessage());
            e.printStackTrace();
            return "Error loading users: " + e.getMessage();
        }
    }

    // ================================= //
    //      Logg inn bruker
    // ================================= //
    public static boolean loginUser(String email, String password) {
        if (userUseCases == null) {
            System.err.println("UserService ikke initialisert");
            return false;
        }
        
        try {
            // Hent alle brukere og finn den med riktig e-post
            ArrayList<User> users = userUseCases.execute();
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    // Sjekk passordet med BCrypt
                    return BCrypt.checkpw(password, user.getPassword());
                }
            }
        } catch (Exception e) {
            System.err.println("Feil ved innlogging: " + e.getMessage());
        }
        
        return false;
    }
    
    // ================================= //
    //      Registrere bruker
    // ================================= //
    public static String registrerUser(String firstname, String lastname, String email, String phonenumber, String password) {
        if (newUserUseCases == null) {
            return "Service ikke initialisert";
        }
        
        // Sjekk om bruker allerede finnes
        if (emailExists(email)) {
            return "E-post allerede i bruk";
        }

        // Opprett ny bruker med eksisterende use case
        try {
            CreateUserCommand command = new CreateUserCommand(firstname, lastname, email, phonenumber, password, "1");
            boolean success = newUserUseCases.execute(command);
            return success ? null : "Feil ved opprettelse av bruker";
        } catch (Exception e) {
            return "Feil ved registrering: " + e.getMessage();
        }
    }

    // Sjekk om e-post allerede finnes
    public static boolean emailExists(String email) {
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
    
    private static String formatUsersAsHtml(ArrayList<User> users) {
        if (users.isEmpty()) {
            return "<li>Ingen brukere funnet</li>";
        }
        
        StringBuilder result = new StringBuilder();
        for (User user : users) {
            result.append("<li>")
                .append(user.getFullName())
                .append(" | ").append(user.getEmail())
                .append(" | ").append(user.getPhoneNumber())
                .append("</li>");
        }
        return result.toString();
    }

    // ================================= //
    //      Hente navn med e-post
    // ================================= //
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