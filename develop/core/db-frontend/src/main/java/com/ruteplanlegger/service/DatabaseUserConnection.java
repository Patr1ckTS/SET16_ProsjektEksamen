package com.ruteplanlegger.service;

import com.ruteplanlegger.adapters.DatabaseUserRepository;
import com.ruteplanlegger.domain.DatabaseSetup;
import com.ruteplanlegger.domain.ports.UserRepository;
import com.ruteplanlegger.useCases.NewUserUseCases;
import com.ruteplanlegger.useCases.UserUseCases;
import com.ruteplanlegger.webRelated.UserListFormatter;

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