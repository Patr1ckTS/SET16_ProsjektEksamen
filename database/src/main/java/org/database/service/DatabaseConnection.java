package com.database.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
import com.database.domain.ports.DatabasePort;

/*
   -Klasse for database-tilkobling-
    Her er det satt opp metoder for å koble til, teste og lukke databaseforbindelser
 */

public class DatabaseConnection implements DatabasePort {
    private final String DB_URL;
    private final String DB_USERNAME;
    private final String DB_PASSWORD;

    public DatabaseConnection(String DB_URL, String DB_USERNAME, String DB_PASSWORD) {
        this.DB_URL = DB_URL;
        this.DB_USERNAME = DB_USERNAME;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    // ===== Database Connection ===== //
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
    
    // ===== Test Connection ===== //
    @Override
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database fungerer!");
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Database feil: " + e.getMessage());
            return false;
        }
    }

    // ===== Close Resources (Unngå memory leaks) ===== //
    @Override
    public void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Feil ved lukking: " + e.getMessage());
        }
    }
}
