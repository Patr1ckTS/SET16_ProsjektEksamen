package org.develop.UserComponent.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private final Connection dbConnection;

    // Constructor Injection - følger Dependency Injection pattern
    public UserService(UserPort userRepository, Connection dbConnection) {
        this.userUseCases = new UserUseCases(userRepository);
        this.newUserUseCases = new NewUserUseCases(userRepository);
        this.formatter = new UserListFormatter();
        this.dbConnection = dbConnection;
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
// Denne funksjonen hasher passordet før det lages           et User objekt:
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            
            CreateUser newUser = new CreateUser(firstName, lastName, email, phonenumber, hashedPassword);
            boolean success = newUserUseCases.newUserValueCheck(newUser);
            
            if (success) {
                databasePermissionGrant(newUser.getEmail(), "itstud.hiof.no");
                return null;
            }
            else {
                return "Feil ved opprettelse av bruker";
            }
        } catch (Exception e) {
            return "Feil ved registrering: " + e.getMessage();
        }
    }

    // Funksjon for rolle-tildeling i databasen. Denne er ikke funksjonell på grunn av delt database-miljø    
    private void databasePermissionGrant(String email, String host) {
        try (Statement stmt = dbConnection.createStatement()) {
            
            String createUser = "CREATE USER IF NOT EXISTS '" + email + "'@'" + host + "'";
            
            String grantPermissions = "GRANT SELECT, INSERT, UPDATE ON se25_G16.* TO '" + email + "'@'" + host + "'";
            
            stmt.execute(createUser);
            stmt.execute(grantPermissions);
            stmt.execute("FLUSH PRIVILEGES");
            
            System.out.println("Database permissions granted for user: " + email);
            
        } catch (SQLException e) {
            System.err.println("Error granting database permissions: " + e.getMessage());
        }
    }

// Metode for uthenting av brukerolle fra database (Ikke enda benyttet i systemet)    
        public String getRole(String email, String host) throws SQLException {
        String sqlRoleQuery = "SHOW GRANTS FOR" + email + "@" + host;

        try (Statement stmt =  dbConnection.createStatement();
             ResultSet rs = stmt.executeQuery(sqlRoleQuery)) {

            while (rs.next()) {
                String grant = rs.getString(1);

                if (grant.contains("GRANT `")) {
                    return grant.split("`")[1];
                }
            }
        }
        return null;
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
                    return user.getUserTypeText();
                }
            }
        } catch (Exception e) {
            System.err.println("Feil ved henting av brukertype: " + e.getMessage());
        }
        return null;
    }
}
