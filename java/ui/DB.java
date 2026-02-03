package ui;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private static final String URL = "jdbc:mariadb://localhost:3306/maintenance";

    private static final String USER = "app";
    private static final String PASSWORD = "app";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
