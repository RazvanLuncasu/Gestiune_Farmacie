package dao;

import model.Medicament;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDAO {

    /**
     * Citește toate medicamentele din baza de date pentru a le afișa în tabel.
     */
    public List<Medicament> getToateMedicamentele() {
        List<Medicament> listaMedicamente = new ArrayList<>();
        String sql = "SELECT * FROM Medicamente";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Medicament m = new Medicament(
                        rs.getInt("id"),
                        rs.getString("denumire"),
                        rs.getString("categorie"),
                        rs.getDouble("pret"),
                        rs.getInt("stoc"),
                        rs.getDate("data_expirare"),
                        rs.getBoolean("necesita_reteta")
                );
                listaMedicamente.add(m);
            }

            stmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("❌ Eroare la citirea medicamentelor: " + e.getMessage());
        }

        return listaMedicamente;
    }

    /**
     * Adaugă un medicament nou în baza de date (apelată din formularul de adăugare).
     */
    public void adaugaMedicament(Medicament m) {
        String sql = "INSERT INTO Medicamente (denumire, categorie, pret, stoc, data_expirare, necesita_reteta) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, m.getDenumire());
            stmt.setString(2, m.getCategorie());
            stmt.setDouble(3, m.getPret());
            stmt.setInt(4, m.getStoc());

            // Folosim java.sql.Date pentru compatibilitate cu MySQL
            stmt.setDate(5, m.getDataExpirare());
            stmt.setBoolean(6, m.isNecesitaReteta());

            // Executăm inserarea
            stmt.executeUpdate();

            stmt.close();

        } catch (SQLException e) {
            System.out.println("❌ Eroare la adăugarea medicamentului: " + e.getMessage());
        }
    }
    /**
     * Actualizează datele unui medicament existent pe baza ID-ului său.
     */
    public void actualizeazaMedicament(Medicament m) {
        String sql = "UPDATE Medicamente SET denumire=?, categorie=?, pret=?, stoc=?, data_expirare=?, necesita_reteta=? WHERE id=?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, m.getDenumire());
            stmt.setString(2, m.getCategorie());
            stmt.setDouble(3, m.getPret());
            stmt.setInt(4, m.getStoc());
            stmt.setDate(5, m.getDataExpirare());
            stmt.setBoolean(6, m.isNecesitaReteta());
            stmt.setInt(7, m.getId()); // ID-ul merge la clauza WHERE

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("❌ Eroare la actualizarea medicamentului: " + e.getMessage());
        }
    }

    /**
     * Șterge un medicament din baza de date folosind ID-ul său.
     */
    public void stergeMedicament(int id) {
        String sql = "DELETE FROM Medicamente WHERE id=?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id); // Setăm ID-ul pe care vrem să-l ștergem

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("❌ Eroare la ștergerea medicamentului: " + e.getMessage());
        }
    }
}