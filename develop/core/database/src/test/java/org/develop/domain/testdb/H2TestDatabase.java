package org.develop.domain.testdb;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * In-memory H2-implementasjon av TestDatabase.
 * --------------------------
 * Hentet fra forelesning 16
 * --------------------------
 * Bruker H2 in-memory database for raske og isolerte tester uten behov for
 * ekstern database. Konfigurert til å etterligne MySQL-dialekt for kompatibilitet
 * med produksjonsdatabasen.
 */
public class H2TestDatabase extends TestDatabase {

    public final static String DB_NAME = "testdb";
    /**
     * jdbc:h2:mem: betyr at databasen skal være en in-memory H2 database
     * MODE=MySQL;DATABASE_TO_LOWER=TRUE; betyr at H2-databasen skal etterligne MySQL
     */
    public final static String URL = "jdbc:h2:mem:" + DB_NAME + ";MODE=MySQL;DATABASE_TO_LOWER=TRUE;";
    public final static String USERNAME = "user";
    public final static String PASSWORD = "password";

    @Override
    public Connection startDB() throws Exception {
        Class.forName("org.h2.Driver"); // Spesifiserer at H2-driver skal benyttes
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return connection;
    }

    @Override
    public void stopDB() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
