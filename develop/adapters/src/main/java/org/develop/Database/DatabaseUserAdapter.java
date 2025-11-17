package org.develop.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.develop.UserComponent.Port.UserPort;
import org.develop.UserComponent.domain.CreateUser;
import org.develop.UserComponent.domain.User;

public class DatabaseUserAdapter implements UserPort {
    private final SQLDatabaseConnection databaseConnection;

    public DatabaseUserAdapter(SQLDatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public ArrayList<User> findAllOrderedByCreatedAt() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT firstname, lastname, email, phonenumber, password, user_type, created_at FROM users ORDER BY created_at DESC";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phonenumber");
                String password = rs.getString("password");
                
                // Hent user_type som streng (støtter både tall og tekst)
                String userType = rs.getString("user_type");
                
                // Håndter null/empty verdier
                if (userType == null || userType.trim().isEmpty()) {
                    userType = "1"; // Default til standard bruker
                }
                
                User user = new User(firstname, lastname, email, phoneNumber, password, userType);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }

        return users;
    }

    public boolean save(CreateUser newUser) {
        String insertSQL = "INSERT INTO users (firstname, lastname, email, phonenumber, password, user_type, created_at) VALUES (?, ?, ?, ?, ?, ?, NOW())";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, newUser.getFirstName());
            pstmt.setString(2, newUser.getLastName());
            pstmt.setString(3, newUser.getEmail());
            pstmt.setString(4, newUser.getPhoneNumber());
            pstmt.setString(5, newUser.getPassword());
            
            // Konverter user_type til tall hvis det er nødvendig
            String userTypeValue = convertUserTypeToNumber(newUser.getUserType());
            pstmt.setString(6, userTypeValue);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error saving user: " + e.getMessage());
            return false;
        }
    }
    
    private String convertUserTypeToNumber(String userType) {
        if (userType == null || userType.trim().isEmpty()) return "1";
        
        switch (userType.toLowerCase().trim()) {
            case "user":
            case "1":
                return "1";
            case "admin":
            case "2":
                return "2";
            case "developer":
            case "3":
                return "3";
            default:
                return "1"; // Default til standard bruker
        }
    }

    @Override
    public ArrayList<User> findAllUsers() {
        return findAllOrderedByCreatedAt();
    }

    @Override
    public boolean saveUser(CreateUser newUser) {
        return save(newUser);
    }
}
