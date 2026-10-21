package dao;

import model.Utilizator;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilizatorDAO {

    /**
     * Verifică dacă username-ul și parola există în baza de date.
     * Returnează un obiect Utilizator dacă datele sunt corecte, sau NULL dacă sunt greșite.
     */
    public Utilizator verificaLogin(String username, String parola) {
        Utilizator utilizatorGasit = null;

        // Interogarea SQL. Folosim "?" pentru a preveni atacurile de tip SQL Injection
        String sql = "SELECT * FROM Utilizatori WHERE username = ? AND parola = ?";

        try {
            // Cerem conexiunea din clasa noastră utilitară
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Înlocuim semnele de întrebare cu datele primite din interfață
            stmt.setString(1, username);
            stmt.setString(2, parola);

            // Executăm căutarea
            ResultSet rs = stmt.executeQuery();

            // Dacă am găsit un rând în tabel (rs.next() e true), populăm modelul
            if (rs.next()) {
                utilizatorGasit = new Utilizator(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("parola"),
                        rs.getString("rol")
                );
            }

            // Nu închidem conexiunea aici, deoarece o va refolosi baza de date
            stmt.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("❌ Eroare la verificarea login-ului: " + e.getMessage());
        }

        return utilizatorGasit;
    }
}