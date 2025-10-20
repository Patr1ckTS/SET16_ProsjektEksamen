package com.ruteplanlegger.domain.ports;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface DatabasePort {
        Connection getConnection() throws SQLException;
        boolean testConnection();
        void closeResources(Connection conn, Statement stmt, ResultSet rs);
}
