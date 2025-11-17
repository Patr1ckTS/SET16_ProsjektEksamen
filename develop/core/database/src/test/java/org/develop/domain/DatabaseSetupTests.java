package org.develop.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.develop.UserComponent.domain.DatabaseSetup;

public class DatabaseSetupTests {
    @Test
    @DisplayName("Control of the DatabaseSetup class constructor")
    public void databaseSetup_checkThatDatabaseSetupAreCreatedWithAllValuesStored(){
//Arrange
        String dbName = "test_db";
        String dbHost = "localhost";
        String dbPort = "3306";
        String dbUsername = "testuser";
        String dbPassword = "testpass";

//Assert
        DatabaseSetup dbSetup = new DatabaseSetup(dbName, dbHost, dbPort, dbUsername, dbPassword);

//Act
        Assertions.assertEquals("jdbc:mysql://localhost:3306/test_db", dbSetup.getDbUrl());
        Assertions.assertEquals(dbName, dbSetup.getDbName());
        Assertions.assertEquals(dbHost, dbSetup.getDbHost());
        Assertions.assertEquals(dbPort, dbSetup.getDbPort());
        Assertions.assertEquals(dbUsername, dbSetup.getDbUsername());
        Assertions.assertEquals(dbPassword, dbSetup.getDbPassword());
    }

    @Test
    @DisplayName("Database info string format check")
    public void databaseSetup_urlStringIsCorrectlyGenerated(){
//Arrange
        DatabaseSetup dbSetup = new DatabaseSetup(
            "test_db", 
            "localhost", 
            "3306", 
            "testuser",
            "testpass");

//Act
        String dbInfo = dbSetup.getDbUrl();

//Assert
        Assertions.assertTrue(dbInfo.contains("jdbc:mysql://localhost:3306/test_db"));
    }

    @Test
    @DisplayName("Database connection test")
    public void databaseSetup_checkConnectionAttachment(){
//Arrange
        DatabaseSetup dbSetup = new DatabaseSetup(
            "test_db", 
            "localhost", 
            "3306", 
            "testuser",
            "password123");
            
//Act
        boolean canConnect = dbSetup.testConnection();

//Assert
        Assertions.assertFalse(canConnect);
    }
}
