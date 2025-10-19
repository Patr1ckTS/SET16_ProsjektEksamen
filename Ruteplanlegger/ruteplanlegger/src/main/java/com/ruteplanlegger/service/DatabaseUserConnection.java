package com.ruteplanlegger.service;

import com.ruteplanlegger.adapters.DatabaseUserRepository;
import com.ruteplanlegger.domain.ports.UserRepository;
import com.ruteplanlegger.useCases.NewUserUseCases;
import com.ruteplanlegger.useCases.UserUseCases;
import com.ruteplanlegger.webRelated.UserListFormatter;

public class DatabaseUserConnection {
    
    public static DatabaseUserOperations createDatabaseUserOperations() {
        String dbUrl = com.ruteplanlegger.DatabaseConfig.getDbUrl();
        String dbUser = com.ruteplanlegger.DatabaseConfig.getDbUsername();
        String dbPassword = com.ruteplanlegger.DatabaseConfig.getDbPassword();
        
        DatabaseConnection databaseConnection = new DatabaseConnection(dbUrl, dbUser, dbPassword);
        
        UserRepository userRepository = new DatabaseUserRepository(databaseConnection);
        
        UserUseCases userUseCases = new UserUseCases(userRepository);
        NewUserUseCases createUserUseCase = new NewUserUseCases(userRepository);  // ADD this missing variable
        UserListFormatter formatter = new UserListFormatter();
     
        return new DatabaseUserOperations(userUseCases, createUserUseCase, formatter);
    }
}