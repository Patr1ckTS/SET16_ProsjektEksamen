package org.develop.service;

import org.develop.adapters.DatabaseUserRepository;
import org.develop.domain.DatabaseSetup;
import org.develop.domain.ports.UserRepository;
import org.develop.useCases.NewUserUseCases;
import org.develop.useCases.UserUseCases;

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
     
        return new DatabaseUserOperations(userUseCases, createUserUseCase);
    }
}