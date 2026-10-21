package dao;

import model.Client;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    /**
     * Extrage toți clienții din baza de date pentru a-i afișa în lista derulantă.
     */
    public List<Client> getTotiClientii() {
        List<Client> listaClienti = new ArrayList<>();
        String sql = "SELECT * FROM clienti";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Client c = new Client(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("telefon")
                );
                listaClienti.add(c);
            }

            stmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("❌ Eroare la citirea clienților: " + e.getMessage());
        }


        return listaClienti;
    }
    public boolean adaugaClient(Client c) {
        String sql = "INSERT INTO clienti (nume, prenume, telefon) VALUES (?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, c.getNume());
            stmt.setString(2, c.getPrenume());
            stmt.setString(3, c.getTelefon());

            stmt.executeUpdate();
            stmt.close();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Eroare la adăugarea clientului: " + e.getMessage());
            return false;
        }
    }
}