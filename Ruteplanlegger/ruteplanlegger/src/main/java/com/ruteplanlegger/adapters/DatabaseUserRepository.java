package com.ruteplanlegger.adapters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ruteplanlegger.domain.model.CreateUserCommand;
import com.ruteplanlegger.domain.model.User;
import com.ruteplanlegger.domain.ports.UserRepository;
import com.ruteplanlegger.service.DatabaseConnection;

/*
    -Database-logikk for uthenting og lagring av brukere-
    Her er det satt opp metoder for å hente og lagre brukere i databasen
*/

public class DatabaseUserRepository implements UserRepository {
    private final DatabaseConnection databaseConnection;

    public DatabaseUserRepository(DatabaseConnection databaseConnection) {
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

    public boolean save(CreateUserCommand command) {
        String insertSQL = "INSERT INTO users (firstname, lastname, email, phonenumber, password, user_type, created_at) VALUES (?, ?, ?, ?, ?, 1, NOW())";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, command.getFirstName());
            pstmt.setString(2, command.getLastName());
            pstmt.setString(3, command.getEmail());
            pstmt.setString(4, command.getPhoneNumber());
            pstmt.setString(5, command.getPassword());

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
    public boolean saveUser(Object command) {
        if (command instanceof CreateUserCommand createUserCommand) {
            return save(createUserCommand);
        }
        throw new IllegalArgumentException("Invalid command type");
    }
}
