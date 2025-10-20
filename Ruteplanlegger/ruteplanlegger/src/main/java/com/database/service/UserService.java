package com.database.service;

import java.util.ArrayList;

import com.database.adapters.DatabaseUserRepository;
import com.database.domain.DatabaseSetup;
import com.database.domain.model.CreateUserCommand;
import com.database.domain.model.User;
import com.database.domain.ports.UserRepository;
import com.database.useCases.NewUserUseCases;
import com.database.useCases.UserUseCases;

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
    
    // Clean application service methods - no SQL!
    public static String getUserFullname() {
        if (userUseCases == null) {
            return "Error: UserService not initialized.";
        }
        
        try {
            ArrayList<User> users = userUseCases.execute();
            return formatUsersAsHtml(users);
        } catch (Exception e) {
            return "Error loading users: " + e.getMessage();
        }
    }
    
    public static boolean addUser(String firstname, String lastname, String email, String phonenumber, String password) {
        if (newUserUseCases == null) {
            return false;
        }
        
        try {
            CreateUserCommand command = new CreateUserCommand(firstname, lastname, email, phonenumber, password, "1");
            return newUserUseCases.execute(command);
        } catch (Exception e) {
            System.err.println("Failed to create user: " + e.getMessage());
            return false;
        }
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
}