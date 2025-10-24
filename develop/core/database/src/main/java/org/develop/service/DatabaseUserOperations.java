package org.develop.service;

import java.util.ArrayList;

import org.develop.domain.CreateUser;
import org.develop.domain.User;
import org.develop.Port.UserRepository;
import org.develop.useCases.NewUserUseCases;
import org.develop.useCases.UserUseCases;

public class DatabaseUserOperations{
    private final UserUseCases usersUseCase;
    private final NewUserUseCases createUserUseCase;
    private final UserListFormatter formatter;
    
    public DatabaseUserOperations(UserRepository userRepository){
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
            CreateUser newUser = new CreateUser(firstname, lastname, email, phonenumber, password, "1"); 
            return createUserUseCase.execute(newUser);
        } catch (Exception e) {
            System.err.println("Failed to add user: " + e.getMessage());
            return false;
        }
    }
}
