package org.develop.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.develop.UserComponent.domain.User;
import org.develop.UserComponent.domain.CreateUser;
import org.develop.UserComponent.Port.UserPort;

public class DatabaseUserAdapter implements UserPort {
    private final SQLDatabaseConnection databaseConnection;

    public DatabaseUserAdapter(SQLDatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public ArrayList<User> findAllOrderedByCreatedAt() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT firstname, lastname, email, phonenumber, password, created_at FROM users ORDER BY created_at DESC";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phonenumber");
                String password = rs.getString("password");

                User user = new User(firstname, lastname, email, phoneNumber, password);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public boolean save(CreateUser newUser) {
        String insertSQL = "INSERT INTO users (firstname, lastname, email, phonenumber, password, user_type, created_at) VALUES (?, ?, ?, ?, ?, 1, NOW())";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, newUser.getFirstName());
            pstmt.setString(2, newUser.getLastName());
            pstmt.setString(3, newUser.getEmail());
            pstmt.setString(4, newUser.getPhoneNumber());
            pstmt.setString(5, newUser.getPassword());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
