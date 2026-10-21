package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Detaliile bazei de date
    private static final String URL = "jdbc:mysql://localhost:3306/farmacie_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Obiectul care ține conexiunea activă
    private static Connection connection = null;

    // Metodă publică și statică pe care o vom apela din DAO-uri
    public static Connection getConnection() {
        try {
            // Dacă nu există o conexiune sau cea veche s-a închis, creăm una nouă
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.out.println("❌ Eroare la conectarea cu baza de date: " + e.getMessage());
        }
        return connection; // Returnăm conexiunea gata de folosit
    }
}