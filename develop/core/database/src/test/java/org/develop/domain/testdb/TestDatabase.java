package org.develop.domain.testdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Abstrakt baseklasse for test-databaser.
 * 
 * Definerer grunnleggende funksjonalitet for alle test-databaser 
 * Krever konkret implementasjon av startDB() og stopDB() ettersom disse
 * 
 * Denne klassen tilbyr hjelpemetoder for å opprette tabeller, sette inn testdata,
 * og verifisere data i tester. Designet for å redusere duplikasjon i testklasser.
 */
public abstract class TestDatabase {

    protected Connection connection;

    public abstract Connection startDB() throws Exception;

    public abstract void stopDB() throws Exception;

    /**
     * Oppretter nødvendige tabeller for testing.
     * Matcher strukturen i MVP-databasen.
     */
    public void createTables() throws Exception {
        try (Statement statement = connection.createStatement()) {
            // Users-tabell (ekskludert user_type, siden den kun er med for MVP-leveransen)
            statement.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "firstName VARCHAR(30), " +
                    "lastName VARCHAR(30), " +
                    "email VARCHAR(50), " +
                    "phonenumber VARCHAR(20), " +
                    "password VARCHAR(100), " +
                    "created_at TIMESTAMP)");

            // Tickets-tabell
            statement.execute("CREATE TABLE IF NOT EXISTS tickets (" +
                    "id INT(11) AUTO_INCREMENT PRIMARY KEY, " +
                    "startTime DATETIME NOT NULL, " +
                    "endTime DATETIME NOT NULL, " +
                    "regionId INT(11) NOT NULL, " +
                    "durationId INT(1) NOT NULL, " +
                    "userId INT(11) NOT NULL)");
        }
    }

    // Oppretter test-brukere som kan refereres i tester
    public void createDummyData() throws Exception {
        try (Statement statement = connection.createStatement()) {
            // Opprett test-brukere
            statement.execute("INSERT INTO users (id, firstName, lastName, email, password) " +
                    "VALUES (1, 'Test', 'User', 'test@example.com', 'password123')");
            statement.execute("INSERT INTO users (firstName, lastName, email, password) " +
                    "VALUES ('Ole', 'Hansen', 'ole@example.com', 'pass456')");
        }
    }

    // Kan verifisere antall rader i en gitt tabell
    public int countRowsInTable(String tableName) throws Exception {
        String sql = "SELECT COUNT(*) FROM " + tableName;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    // Teller billetter med gitt regionId
    public int countTicketsByRegion(int regionId) throws Exception {
        String sql = "SELECT COUNT(*) FROM tickets WHERE regionId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, regionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        }
    }
}
