package com.database.domain.ports;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*
        -Interface for å definere nødvendige verdier ved tilkobling av database-
        Metoder for:
        - Hente tilkobling
        - Teste tilkobling
        - Lukke ressurser
*/

public interface DatabasePort {
        Connection getConnection() throws SQLException;
        boolean testConnection();
        void closeResources(Connection conn, Statement stmt, ResultSet rs);
}
