package org.develop.domain.TicketTests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.develop.TicketComponent.Adapter.SaveTicketToDatabase;
import org.develop.TicketComponent.Domain.NewTicket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SaveTicketToDatabaseTests {

    private Connection testConnection;
    private SaveTicketToDatabase saveTicketToDatabase;

    @BeforeEach
    public void setUp() {
// Advarsel: Krever faktisk databasetilkobling. Kan mockes på sikt
        try {
            String url = "jdbc:mysql://itstud.hiof.no:3306/se25_G16";
            String user = "gruppe16";
            String password = "Summer35";
            testConnection = DriverManager.getConnection(url, user, password);
            saveTicketToDatabase = new SaveTicketToDatabase(testConnection);
        } catch (SQLException e) {
            // Test vil feile hvis databasen ikke er tilgjengelig
            System.out.println("Database connection failed in test setup: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            if (testConnection != null && !testConnection.isClosed()) {
                testConnection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing test connection: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("SaveTicketToDatabase successfully saves valid ticket")
    public void saveTicketToDatabase_SavesValidTicketSuccessfully() {
        // Arrange
        // Forutsetter at bruker med ID 1 eksisterer i databasen
        LocalDateTime endTime = LocalDateTime.now().plusDays(7);
        int region = 2;
        int userId = 1;
        NewTicket testTicket = new NewTicket(endTime, region, userId);

        // Act & Assert
        // Siden saveTicket() ikke returnerer noe, tester vi at ingen exception kastes
        Assertions.assertDoesNotThrow(() -> {
            saveTicketToDatabase.saveTicket(testTicket);
        });
    }


    @Test
    @DisplayName("SaveTicketToDatabase handles ticket with future end time")
    public void saveTicketToDatabase_HandlesTicketWithFutureEndTime() {
        // Arrange
        LocalDateTime futureEndTime = LocalDateTime.now().plusYears(1);
        int region = 1;
        int userId = 1;
        NewTicket futureTicket = new NewTicket(futureEndTime, region, userId);

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            saveTicketToDatabase.saveTicket(futureTicket);
        });
    }
}