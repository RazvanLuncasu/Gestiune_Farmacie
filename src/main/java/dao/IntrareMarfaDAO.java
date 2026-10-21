package dao;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IntrareMarfaDAO {

    public boolean inregistreazaIntrare(int idMedicament, String furnizor, int cantitate) {
        Connection conn = DatabaseConnection.getConnection();

        try {
            conn.setAutoCommit(false); // Începem tranzacția

            // 1. Înregistrăm intrarea în istoric
            String sqlIntrare = "INSERT INTO Intrari_Marfa (id_medicament, nume_furnizor, cantitate, data_intrare) VALUES (?, ?, ?, NOW())";
            PreparedStatement stmtIntrare = conn.prepareStatement(sqlIntrare);
            stmtIntrare.setInt(1, idMedicament);
            stmtIntrare.setString(2, furnizor);
            stmtIntrare.setInt(3, cantitate);
            stmtIntrare.executeUpdate();
            stmtIntrare.close();

            // 2. Adăugăm cantitatea la stocul existent (Atenție: aici facem +, la vânzări făceam -)
            String sqlUpdateStoc = "UPDATE Medicamente SET stoc = stoc + ? WHERE id = ?";
            PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdateStoc);
            stmtUpdate.setInt(1, cantitate);
            stmtUpdate.setInt(2, idMedicament);
            stmtUpdate.executeUpdate();
            stmtUpdate.close();

            // Salvăm modificările
            conn.commit();
            conn.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Eroare la recepția mărfii: " + e.getMessage());
            try {
                conn.rollback(); // Anulăm dacă apare o eroare
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
}