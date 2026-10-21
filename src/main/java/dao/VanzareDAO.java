package dao;

import model.Medicament;
import util.DatabaseConnection;

import java.sql.*;
import java.util.List;

public class VanzareDAO {

    public boolean inregistreazaVanzare(int idUtilizator, int idClient, List<Medicament> cosCumparaturi) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            // 1. Inserăm rândul în Vanzari (verificăm dacă avem client sau e pe "Standard")
            String sqlVanzare;
            PreparedStatement stmtVanzare;

            if (idClient > 0) {
                sqlVanzare = "INSERT INTO vanzari (id_utilizator, id_client, data_vanzare) VALUES (?, ?, NOW())";
                stmtVanzare = conn.prepareStatement(sqlVanzare, Statement.RETURN_GENERATED_KEYS);
                stmtVanzare.setInt(1, idUtilizator);
                stmtVanzare.setInt(2, idClient);
            } else {
                sqlVanzare = "INSERT INTO vanzari (id_utilizator, data_vanzare) VALUES (?, NOW())";
                stmtVanzare = conn.prepareStatement(sqlVanzare, Statement.RETURN_GENERATED_KEYS);
                stmtVanzare.setInt(1, idUtilizator);
            }

            stmtVanzare.executeUpdate();

            // Preluăm ID-ul bonului creat
            ResultSet rs = stmtVanzare.getGeneratedKeys();
            int idVanzare = 0;
            if (rs.next()) {
                idVanzare = rs.getInt(1);
            }
            stmtVanzare.close();

            // 2. Inserăm produsele și scădem stocul
            String sqlDetalii = "INSERT INTO detalii_vanzari (id_vanzare, id_medicament, cantitate, pret_la_vanzare) VALUES (?, ?, ?, ?)";
            String sqlUpdateStoc = "UPDATE medicamente SET stoc = stoc - ? WHERE id = ?";

            PreparedStatement stmtDetalii = conn.prepareStatement(sqlDetalii);
            PreparedStatement stmtStoc = conn.prepareStatement(sqlUpdateStoc);

            for (Medicament m : cosCumparaturi) {
                stmtDetalii.setInt(1, idVanzare);
                stmtDetalii.setInt(2, m.getId());
                stmtDetalii.setInt(3, m.getStoc());
                stmtDetalii.setDouble(4, m.getPret());
                stmtDetalii.addBatch();

                stmtStoc.setInt(1, m.getStoc());
                stmtStoc.setInt(2, m.getId());
                stmtStoc.addBatch();
            }

            stmtDetalii.executeBatch();
            stmtStoc.executeBatch();

            conn.commit();
            conn.setAutoCommit(true);

            stmtDetalii.close();
            stmtStoc.close();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Eroare la procesarea vânzării: " + e.getMessage());
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
}