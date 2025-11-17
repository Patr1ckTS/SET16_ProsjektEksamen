package org.develop.TicketComponent.Adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.develop.TicketComponent.Domain.NewTicket;
import org.develop.TicketComponent.Port.Interface.TicketRepository;

/*
    -For å sende nye billetter til databasen-
    Denne skal på sikt i database-delen av prosjektet.

    Uthenter data i Ticket format. 
*/

public class SaveTicketToDatabase implements TicketRepository {
    private Connection connection;

    public SaveTicketToDatabase(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveTicket(NewTicket newTicket) {
        String sql = "INSERT INTO tickets (startTime, endTime, regionId, durationId, userId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(newTicket.getStartTime()));
            pstmt.setTimestamp(2, Timestamp.valueOf(newTicket.getEndTime()));
            pstmt.setInt(3, (newTicket.getRegion()));
            pstmt.setInt(4, 1); // Standard durationId for tester (kan endres senere)
            pstmt.setInt(5, newTicket.getUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
