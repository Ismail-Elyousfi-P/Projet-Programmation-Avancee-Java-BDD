import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    // Connexion à MariaDB exposé en local par Docker (port 3306)
    private static final String URL = "jdbc:mariadb://localhost:3306/maintenance";

    // Utilisateur créé par docker-compose.yml
    private static final String USER = "app";
    private static final String PASSWORD = "app";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
