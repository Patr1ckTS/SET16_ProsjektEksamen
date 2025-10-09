package com.ruteplanlegger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    public static String getUserFullname() {
        String result = "";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT firstname, lastname, email, phonenumber, user_type, created_at FROM users ORDER BY created_at DESC")) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String email = rs.getString("email");
                String phonenumber = rs.getString("phonenumber");
                int userType = rs.getInt("user_type");
                String createdAt = rs.getString("created_at");
                result += "<li>" + firstname + " " + lastname + " | " + email + " | " + phonenumber + " | Type: " + userType + " | Opprettet: " + createdAt + "</li>\n";
            }
            
        } catch (SQLException e) {
            result = "Feil: " + e.getMessage();
        }
        
        return result;
    }


    public static boolean addUser(String firstname, String lastname, String email, String phonenumber, String password) {
        String sql = "INSERT INTO users (firstname, lastname, email, phonenumber, password, user_type, created_at) VALUES (?, ?, ?, ?, ?, 1, NOW())";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, email);
            pstmt.setString(4, phonenumber);
            pstmt.setString(5, password);
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Feil ved lagring av bruker: " + e.getMessage());
            return false;
        }
    }

}
