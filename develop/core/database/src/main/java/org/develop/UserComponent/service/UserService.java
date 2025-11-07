package org.develop.UserComponent.service;

import java.util.ArrayList;

import org.develop.UserComponent.Port.UserPort;
import org.develop.UserComponent.domain.CreateUser;
import org.develop.UserComponent.domain.User;
import org.develop.UserComponent.useCases.NewUserUseCases;
import org.develop.UserComponent.useCases.UserUseCases;
import org.mindrot.jbcrypt.BCrypt;

// Service for håndtering av brukeroperasjoner
// Bruker Dependency Injection via constructor for UserPort
// Håndterer kall til UseCases og formatering av data for presentasjon

public class UserService {
    private final UserUseCases userUseCases;
    private final NewUserUseCases newUserUseCases;
    private final UserListFormatter formatter;
    private final RoleService roleService;

    // Constructor Injection - følger Dependency Injection pattern
    public UserService(UserPort userRepository, RoleService roleService) {
        this.userUseCases = new UserUseCases(userRepository);
        this.newUserUseCases = new NewUserUseCases(userRepository);
        this.formatter = new UserListFormatter();
        this.roleService = roleService;
    }

    public String getUserFullname() {
        try {
            ArrayList<User> users = userUseCases.arrayListOfRequestedUsers();
            return formatter.formatUserList(users);
        } catch (Exception e) {
            return "<li>Feil ved henting av brukere: " + e.getMessage() + "</li>";
        }
    }

    public boolean loginUser(String email, String password) {
        try {
            ArrayList<User> users = userUseCases.arrayListOfRequestedUsers();
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

    public String registerUser(String firstName, String lastName, String email, String phonenumber, String password) {
        if (emailExists(email)) {
            return "E-post allerede i bruk";
        }

        try {
            CreateUser newUser = new CreateUser(firstName, lastName, email, phonenumber, password);
            boolean success = newUserUseCases.newUserValueCheck(newUser);
            
            if (success) {
                roleService.setDefaultRole(newUser.getEmail(), "`se25_G16`");
                return null;
            }
            else {
                return "Feil ved opprettelse av bruker";
            }
        } catch (Exception e) {
            return "Feil ved registrering: " + e.getMessage();
        }
    }

    private boolean emailExists(String email) {
        try {
            ArrayList<User> users = userUseCases.arrayListOfRequestedUsers();
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

    public String getNameByEmail(String email) {
        try {
            ArrayList<User> users = userUseCases.arrayListOfRequestedUsers();
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

    public String getPhoneNumberByEmail(String email) {
        try {
            ArrayList<User> users = userUseCases.arrayListOfRequestedUsers();
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    return user.getPhoneNumber();
                }
            }
        } catch (Exception e) {
            System.err.println("Feil ved henting av telefonnummer: " + e.getMessage());
        }
        return null;
    }

    public String getUserTypeByEmail(String email) {
        try {
            ArrayList<User> users = userUseCases.arrayListOfRequestedUsers();
            for (User user : users) {
                if (user.getEmail().equals(email)) {
//                    return user.getUserTypeText();
                    return null;
                }
            }
        } catch (Exception e) {
            System.err.println("Feil ved henting av brukertype: " + e.getMessage());
        }
        return null;
    }
}
