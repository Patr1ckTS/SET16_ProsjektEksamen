package com.database.service;

import com.database.adapters.DatabaseUserRepository;
import com.database.domain.DatabaseSetup;
import com.database.domain.ports.UserRepository;
import com.database.useCases.NewUserUseCases;
import com.database.useCases.UserUseCases;
import com.web.UserListFormatter;

//  -Klasse for opprettelse av DatabaseUserOperations-objekter-

public class DatabaseUserConnection {

    public static DatabaseUserOperations createDatabaseUserOperations(DatabaseSetup dbSetup) {
        DatabaseConnection databaseConnection = new DatabaseConnection(
            dbSetup.getDbUrl(), 
            dbSetup.getDbUsername(), 
            dbSetup.getDbPassword()
        );
        
        UserRepository userRepository = new DatabaseUserRepository(databaseConnection);
        
        UserUseCases userUseCases = new UserUseCases(userRepository);
        NewUserUseCases createUserUseCase = new NewUserUseCases(userRepository);  
        UserListFormatter formatter = new UserListFormatter();
     
        return new DatabaseUserOperations(userUseCases, createUserUseCase, formatter);
    }
}