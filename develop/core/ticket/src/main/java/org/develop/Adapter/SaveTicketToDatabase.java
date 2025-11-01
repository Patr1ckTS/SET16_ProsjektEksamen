package org.develop.Adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.develop.Domain.NewTicket;
import org.develop.Domain.User;
import org.develop.Port.Interface.TicketRepository;

/*
    -For å sende nye billetter til databasen-
    Denne skal på sikt i database-delen av prosjektet.

    Uthenter data i Ticket format. På sikt burde region
    anskje hentes ut fra referanse-tabellen for regioner.
*/

public class SaveTicketToDatabase implements TicketRepository {
    private Connection connection;

    public SaveTicketToDatabase(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveTicket(NewTicket newTicket) {
        String sql = "INSERT INTO tickets (startTime, endTime, regionId, userId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(newTicket.getStartTime()));
            pstmt.setTimestamp(2, Timestamp.valueOf(newTicket.getEndTime()));
            pstmt.setInt(3, (newTicket.getRegion()));
            pstmt.setInt(4, newTicket.getUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    } 

// For å lage en instans av User-klassen med eks. data fra databasen   
    public User findUserById(Connection connection, int id){
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phonenumber");
                String password = rs.getString("password");
                
                return new User(id, firstName, lastName, email, phoneNumber, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }


}
