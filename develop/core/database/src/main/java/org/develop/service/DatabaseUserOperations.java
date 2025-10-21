package org.develop.service;

import java.util.ArrayList;

import org.develop.adapters.DatabaseUserRepository;
import org.develop.domain.model.CreateUserCommand;
import org.develop.domain.model.User;
import org.develop.domain.ports.UserRepository;
import org.develop.useCases.NewUserUseCases;
import org.develop.useCases.UserUseCases;

//  -Wrapper klasse for bruker operasjoner mot databasen-
public class DatabaseUserOperations{
    private final UserUseCases usersUseCase;
    private final NewUserUseCases createUserUseCase;
    
    public DatabaseUserOperations(String dbUrl, String dbUser, String dbPassword){
        DatabaseConnection dbConnection = new DatabaseConnection(dbUrl, dbUser, dbPassword);
        UserRepository userRepository = new DatabaseUserRepository(dbConnection);
        
        this.usersUseCase = new UserUseCases(userRepository);
        this.createUserUseCase = new NewUserUseCases(userRepository);
    }

    public DatabaseUserOperations(UserUseCases userUseCaseParam,
                                  NewUserUseCases createUserUseCase) {
        this.usersUseCase = userUseCaseParam;
        this.createUserUseCase = createUserUseCase;
    }

    public String getFullName(){
        try{
            ArrayList<User> users = usersUseCase.execute();
            // Formatering inline (samme logikk som UserListFormatter)
            return formatUserList(users);
        }
        catch(Exception e){
            return "Feil: " + e.getMessage();
        }
    }
    
    // Privat metode for formatering (samme logikk som før)
    private String formatUserList(ArrayList<User> users){
        if(users.isEmpty()){
            return "<li>Ingen brukere funnet</li>";
        }

        StringBuilder result = new StringBuilder();
        for(User user : users){
            result.append("<li>")
                .append(user.getFullName())
                .append(" | ").append(user.getEmail())
                .append(" | ").append(user.getPhoneNumber())
                .append("</li>");
        }
        return result.toString();
    }
    
    public boolean addUser(String firstname, String lastname, String email, String phonenumber, String password){
        try {
            CreateUserCommand command = new CreateUserCommand(firstname, lastname, email, phonenumber, password, "1"); 
            return createUserUseCase.execute(command);
        } catch (Exception e) {
            System.err.println("Failed to add user: " + e.getMessage());
            return false;
        }
    }
    
}

