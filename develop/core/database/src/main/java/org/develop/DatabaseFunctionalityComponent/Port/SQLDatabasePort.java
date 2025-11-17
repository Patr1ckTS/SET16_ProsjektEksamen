package org.develop.DatabaseFunctionalityComponent.Port;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface SQLDatabasePort {
    Connection getConnection() throws SQLException;
    boolean testConnection();
    void closeResources(Connection conn, Statement stmt, ResultSet rs);
}
