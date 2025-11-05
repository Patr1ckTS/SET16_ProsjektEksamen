package org.develop.TicketComponent.Adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.develop.TicketComponent.Domain.Ticket;

/*
    -For å uthente billetter fra databasen-
    Denne skal på sikt i database-delen av prosjektet.

    Uthenter data i Ticket format. På sikt burde region
    anskje hentes ut fra referanse-tabellen for regioner.
*/

public class RetrieveTicketsFromDatabase {
    ArrayList<Ticket> tickets = new ArrayList<>();
    Connection connection;
        
    public RetrieveTicketsFromDatabase(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Ticket> findTicketByUserId(int userId) { 
        String sql = "SELECT * FROM tickets WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int ticketId = rs.getInt("id");
                Timestamp startTime = rs.getTimestamp("startTime");
                Timestamp endTime = rs.getTimestamp("start");
                int regionId = rs.getInt("regionId");

                Ticket ticket = new Ticket(
                    ticketId, 
                    startTime.toLocalDateTime(), 
                    endTime.toLocalDateTime(), 
                    regionId, 
                    userId);
                
                tickets.add(ticket);
                
                return tickets;
            }

            } catch (SQLException e) {
                System.out.println("Error finding ticket: " + e.getMessage());
        }
        return null;
    }
}
