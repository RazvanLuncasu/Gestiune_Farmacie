package GUI;

import dao.MedicamentDAO;
import model.Medicament;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPanel extends JPanel {

    private MainFrame parinte;
    private JTable tabelMedicamente;
    private DefaultTableModel modelTabel;

    public AdminPanel(MainFrame parinte) {
        this.parinte = parinte;
        setLayout(new BorderLayout()); // BorderLayout este perfect pentru a pune un tabel pe tot centrul

        // --- 1. PARTEA DE SUS (Titlu și Buton de Logout) ---
        JPanel panouSus = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblTitlu = new JLabel("Panou Administrator - Gestiune Stoc");
        lblTitlu.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnLogout = new JButton("Delogare");
        btnLogout.addActionListener(e -> parinte.schimbaEcranul("PanouLogin"));

        panouSus.add(lblTitlu);
        panouSus.add(Box.createHorizontalStrut(200));
        panouSus.add(btnLogout);
        add(panouSus, BorderLayout.NORTH);

        // --- 2. PARTEA CENTRALĂ (Tabelul) ---
        String[] coloane = {"ID", "Denumire", "Categorie", "Preț", "Stoc", "Expiră la", "Rețetă?"};
        modelTabel = new DefaultTableModel(coloane, 0);
        tabelMedicamente = new JTable(modelTabel);

        JScrollPane scrollPane = new JScrollPane(tabelMedicamente);
        add(scrollPane, BorderLayout.CENTER);

        // Încărcăm datele imediat cum se creează panoul
        incarcaDateInTabel();

        // --- 3. PARTEA DE JOS (Butoane de acțiune) ---
        JPanel panouJos = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        JButton btnAdauga = new JButton("Adaugă");
        JButton btnEditeaza = new JButton("Editează");
        JButton btnSterge = new JButton("Șterge");
        JButton btnReceptie = new JButton("Aprovizionare Stoc"); // Aici am adăugat butonul

        panouJos.add(btnAdauga);
        panouJos.add(btnEditeaza);
        panouJos.add(btnSterge);
        panouJos.add(btnReceptie); // Îl adăugăm în interfață
        add(panouJos, BorderLayout.SOUTH);

        // --- ACȚIUNILE BUTOANELOR ---

        // Acțiune ADĂUGARE
        btnAdauga.addActionListener(e -> {
            AdaugaMedicamentDialog dialog = new AdaugaMedicamentDialog(parinte, this);
            dialog.setVisible(true);
        });

        // Acțiune EDITARE
        btnEditeaza.addActionListener(e -> {
            int randSelectat = tabelMedicamente.getSelectedRow();

            if (randSelectat == -1) {
                JOptionPane.showMessageDialog(this, "Selectați un medicament din tabel pentru a-l edita!", "Atenție", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = (int) modelTabel.getValueAt(randSelectat, 0);
            String denumire = (String) modelTabel.getValueAt(randSelectat, 1);
            String categorie = (String) modelTabel.getValueAt(randSelectat, 2);
            double pret = (double) modelTabel.getValueAt(randSelectat, 3);
            int stoc = (int) modelTabel.getValueAt(randSelectat, 4);
            java.sql.Date dataExpirare = (java.sql.Date) modelTabel.getValueAt(randSelectat, 5);
            boolean necesitaReteta = modelTabel.getValueAt(randSelectat, 6).equals("Da");

            EditeazaMedicamentDialog dialog = new EditeazaMedicamentDialog(parinte, this, id, denumire, categorie, pret, stoc, dataExpirare, necesitaReteta);
            dialog.setVisible(true);
        });

        // Acțiune ȘTERGERE
        btnSterge.addActionListener(e -> {
            int randSelectat = tabelMedicamente.getSelectedRow();

            if (randSelectat == -1) {
                JOptionPane.showMessageDialog(this, "Selectați un medicament pentru a-l șterge!", "Atenție", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int raspuns = JOptionPane.showConfirmDialog(this, "Sunteți sigur că doriți să ștergeți acest medicament?", "Confirmare ștergere", JOptionPane.YES_NO_OPTION);

            if (raspuns == JOptionPane.YES_OPTION) {
                int id = (int) modelTabel.getValueAt(randSelectat, 0);

                MedicamentDAO dao = new MedicamentDAO();
                dao.stergeMedicament(id);

                incarcaDateInTabel();
                JOptionPane.showMessageDialog(this, "Medicament șters cu succes!");
            }
        });

        // Acțiune RECEPȚIE MARFĂ (APROVIZIONARE)
        btnReceptie.addActionListener(e -> {
            int randSelectat = tabelMedicamente.getSelectedRow();

            if (randSelectat == -1) {
                JOptionPane.showMessageDialog(this, "Selectați un medicament din tabel pentru a face recepția!", "Atenție", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = (int) modelTabel.getValueAt(randSelectat, 0);
            String denumire = (String) modelTabel.getValueAt(randSelectat, 1);

            // Deschidem fereastra de recepție pe care am creat-o la pasul 3
            ReceptieDialog dialog = new ReceptieDialog(parinte, this, id, denumire);
            dialog.setVisible(true);
        });
    }

    // --- METODE UTILITARE ---

    // Metodă care ia datele din baza de date și le pune în JTable
    public void incarcaDateInTabel() {
        modelTabel.setRowCount(0); // Curățăm tabelul înainte de a-l umple

        MedicamentDAO dao = new MedicamentDAO();
        List<Medicament> medicamente = dao.getToateMedicamentele();

        for (Medicament m : medicamente) {
            Object[] rand = {
                    m.getId(),
                    m.getDenumire(),
                    m.getCategorie(),
                    m.getPret(),
                    m.getStoc(),
                    m.getDataExpirare(),
                    m.isNecesitaReteta() ? "Da" : "Nu"
            };
            modelTabel.addRow(rand);
        }
    }
}