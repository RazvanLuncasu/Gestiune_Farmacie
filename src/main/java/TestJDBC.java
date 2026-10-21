import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestJDBC {

    private static final String URL = "jdbc:mysql://localhost:3306/farmacie_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexiune la baza de date reușită!");

            // 1. INSERT 
            String insertSQL = "INSERT INTO Utilizatori (username, parola, rol) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertSQL);
            insertStmt.setString(1, "test_farmacist");
            insertStmt.setString(2, "parola123");
            insertStmt.setString(3, "FARMACIST");
            insertStmt.executeUpdate();
            System.out.println("✅ INSERT: Utilizatorul a fost adăugat cu succes!");

            // 2. SELECT 
            System.out.println("✅ SELECT: Afișăm utilizatorii din baza de date:");
            String selectSQL = "SELECT * FROM Utilizatori";
            PreparedStatement selectStmt = connection.prepareStatement(selectSQL);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                System.out.println("   ID: " + resultSet.getInt("id") +
                        " | Username: " + resultSet.getString("username") +
                        " | Rol: " + resultSet.getString("rol"));
            }

            // 3. UPDATE 
            String updateSQL = "UPDATE Utilizatori SET rol = ? WHERE username = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateSQL);
            updateStmt.setString(1, "ADMIN");
            updateStmt.setString(2, "test_farmacist");
            updateStmt.executeUpdate();
            System.out.println("✅ UPDATE: Rolul a fost modificat!");

            // 4. DELETE 
            String deleteSQL = "DELETE FROM Utilizatori WHERE username = ?";
            PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL);
            deleteStmt.setString(1, "test_farmacist");
            deleteStmt.executeUpdate();
            System.out.println("✅ DELETE: Utilizatorul a fost șters!");

            connection.close();

        } catch (Exception e) {
            System.out.println("❌ Eroare:");
            e.printStackTrace();
        }
    }
}