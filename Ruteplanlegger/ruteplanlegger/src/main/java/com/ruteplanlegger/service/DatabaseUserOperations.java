package com.ruteplanlegger.service;

import java.util.ArrayList;

import com.ruteplanlegger.adapters.DatabaseUserRepository;
import com.ruteplanlegger.domain.model.CreateUserCommand;
import com.ruteplanlegger.domain.model.User;
import com.ruteplanlegger.domain.ports.UserRepository;
import com.ruteplanlegger.useCases.NewUserUseCases;
import com.ruteplanlegger.useCases.UserUseCases;
import com.ruteplanlegger.webRelated.UserListFormatter;

public class DatabaseUserOperations{
    private final UserUseCases usersUseCase;
    private final NewUserUseCases createUserUseCase;
    private final UserListFormatter formatter;
    
    public DatabaseUserOperations(String dbUrl, String dbUser, String dbPassword){
        DatabaseConnection dbConnection = new DatabaseConnection(dbUrl, dbUser, dbPassword);
        UserRepository userRepository = new DatabaseUserRepository(dbConnection);
        
        this.usersUseCase = new UserUseCases(userRepository);
        this.createUserUseCase = new NewUserUseCases(userRepository);
        this.formatter = new UserListFormatter();
    }

    public DatabaseUserOperations(UserUseCases userUseCaseParam,
                                  NewUserUseCases createUserUseCase,
                                  UserListFormatter formatter) {
        this.usersUseCase = userUseCaseParam;
        this.createUserUseCase = createUserUseCase;
        this.formatter = formatter;
    }

    public String getFullName(){
        try{
            ArrayList<User> users = usersUseCase.execute();
            return formatter.formatUserList(users);
        }
        catch(Exception e){
            return "Feil: " + e.getMessage();
        }
    }
    public boolean addUser(String firstname, String lastname, String email, String phonenumber, String password){
        try {
            String fullName = firstname + " " + lastname;
            CreateUserCommand command = new CreateUserCommand(firstname, lastname, email, phonenumber, password, "1"           ); 
            return createUserUseCase.execute(command);
        } catch (Exception e) {
            System.err.println("Failed to add user: " + e.getMessage());
            return false;
        }    }
    
}

