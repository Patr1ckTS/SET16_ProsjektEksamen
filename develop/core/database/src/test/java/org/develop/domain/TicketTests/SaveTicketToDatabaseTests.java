package org.develop.domain.TicketTests;

import java.sql.Connection;
import java.time.LocalDateTime;

import org.develop.TicketComponent.Adapter.SaveTicketToDatabase;
import org.develop.TicketComponent.Domain.NewTicket;
import org.develop.domain.testdb.H2TestDatabase;
import org.develop.domain.testdb.TestDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Integrasjonstest for SaveTicketToDatabase med H2 in-memory database.
 *
 * Bruker TestDatabase-hierarkiet for å håndtere databaseoppsett og hjelpemetoder.
 * Dette gjør testene enklere å vedlikeholde og reduserer duplikasjon.
 */
public class SaveTicketToDatabaseTests {

    private TestDatabase testDatabase;
    private Connection testConnection;
    private SaveTicketToDatabase saveTicketToDatabase;

    @BeforeEach
    public void setUp() {
        try {
            // Opprett og start H2 test-database
            testDatabase = new H2TestDatabase();
            testConnection = testDatabase.startDB();

            // Opprett tabeller og test-bruker
            testDatabase.createTables();
            testDatabase.createDummyData();

            saveTicketToDatabase = new SaveTicketToDatabase(testConnection);
        } catch (Exception e) {
            Assertions.fail("H2 database setup failed: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            if (testDatabase != null) {
                testDatabase.stopDB();
            }
        } catch (Exception e) {
            System.out.println("Error closing test database: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("SaveTicketToDatabase successfully saves valid ticket")
    public void saveTicketToDatabase_SavesValidTicketSuccessfully() throws Exception {
        // Arrange
        NewTicket testTicket = new NewTicket(LocalDateTime.now().plusDays(7), 2, 1);

        // Act
        saveTicketToDatabase.saveTicket(testTicket);

        // Assert - Verifiser at billetten ble lagret
        Assertions.assertEquals(1, testDatabase.countTicketsByRegion(2), 
                "En billett med region 2 skal lagres");
    }

    @Test
    @DisplayName("SaveTicketToDatabase handles ticket with future end time")
    public void saveTicketToDatabase_HandlesTicketWithFutureEndTime() throws Exception {
        // Arrange
        NewTicket futureTicket = new NewTicket(LocalDateTime.now().plusYears(1), 1, 1);

        // Act
        saveTicketToDatabase.saveTicket(futureTicket);

        // Assert
        Assertions.assertEquals(1, testDatabase.countTicketsByRegion(1), 
                "En billett med region 1 skal lagres");
    }

}