package org.develop.UserComponent.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseSetup {
    private final String DB_NAME;
    private final String DB_HOST;
    private final String DB_PORT;
    private final String DB_USERNAME;
    private final String DB_PASSWORD;
    private final String DB_URL;

    public DatabaseSetup(String DB_NAME, String DB_HOST, String DB_PORT, String DB_USERNAME, String DB_PASSWORD) {
        this.DB_NAME = DB_NAME;
        this.DB_HOST = DB_HOST;
        this.DB_PORT = DB_PORT;
        this.DB_USERNAME = DB_USERNAME;
        this.DB_PASSWORD = DB_PASSWORD;
        this.DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
    }

    public String getDbUrl() { 
        return DB_URL; 
    }
 
    public String getDbUsername() { 
        return DB_USERNAME; 
    }

    public String getDbName() { 
        return DB_NAME; 
    }
    
    public String getDbHost() { 
        return DB_HOST; 
    }
    
    public String getDbPort() { 
        return DB_PORT; 
    }
    
    public String getDbPassword() { 
        return DB_PASSWORD; 
    }

    public String showDatabaseInfo() {
        return """
               ===== Database Info =====
               Host: %s
               Port: %s
               Name: %s
               Username: %s
               =========================""".formatted(DB_HOST, DB_PORT, DB_NAME, DB_USERNAME);
    }
    
    public boolean testConnection() {
        try (Connection conn = DriverManager.getConnection(getDbUrl(), getDbUsername(), getDbPassword())) {
            System.out.println("Database fungerer!");
            return true;
        } catch (SQLException e) {
            System.out.println("Database feil: " + e.getMessage());
            return false;
        }
    }
}
