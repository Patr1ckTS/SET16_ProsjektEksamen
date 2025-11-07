package org.develop.UserComponent.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RoleService {
    private final Connection connection;

    public RoleService(Connection connection) {
        this.connection = connection;
    }

    public void setDefaultRole(String email, String host) {
        try (Statement stmt = connection.createStatement()) {
            String sqlSetRole = "GRANT ROLE 'user'@'" + host + "' TO '" + email + "'@'" + host + "'";

            stmt.execute(sqlSetRole);
        } catch (SQLException e) {
            System.out.println("Error setting role: " + e.getMessage());
        }
    }

    public String getRole(String email, String host) throws SQLException {
        String sqlRoleQuery = "SHOW GRANTS FOR" + email + "@" + host;

        try (Statement stmt =  connection.createStatement();
             ResultSet rs = stmt.executeQuery(sqlRoleQuery)) {

            while (rs.next()) {
                String grant = rs.getString(1);

                if (grant.contains("GRANT `")) {
                    return grant.split("`")[1];
                }
            }
        }
        return null;
    }
}
