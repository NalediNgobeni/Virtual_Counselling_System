package za.ac.cput.com.studentportalgui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    // Database connection configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/carelink?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final  String DB_PASS = ""; // Empty string if no password in XAMPP

    public static Connection connect() throws SQLException {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Make sure the driver is added to your classpath.", e);
        }

        // Create connection
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        conn.setAutoCommit(true);
        return conn;
    }
}
