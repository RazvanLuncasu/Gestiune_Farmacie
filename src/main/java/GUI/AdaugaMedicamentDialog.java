package GUI;

import dao.MedicamentDAO;
import model.Medicament;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class AdaugaMedicamentDialog extends JDialog {

    private JTextField txtDenumire, txtCategorie, txtPret, txtStoc, txtDataExpirare;
    private JCheckBox chkReteta;
    private JButton btnSalveaza, btnAnuleaza;
    private AdminPanel parintePanel; // Avem nevoie de referință ca să dăm refresh la tabel

    public AdaugaMedicamentDialog(JFrame parinteFrame, AdminPanel parintePanel) {
        super(parinteFrame, "Adaugă Medicament Nou", true); // 'true' blochează fereastra din spate până o închizi pe asta
        this.parintePanel = parintePanel;

        setSize(400, 350);
        setLocationRelativeTo(parinteFrame);
        setLayout(new GridLayout(7, 2, 10, 10)); // Formular tip grilă: 7 rânduri, 2 coloane

        // Inițializare componente formular
        add(new JLabel("  Denumire:"));
        txtDenumire = new JTextField();
        add(txtDenumire);

        add(new JLabel("  Categorie:"));
        txtCategorie = new JTextField();
        add(txtCategorie);

        add(new JLabel("  Preț:"));
        txtPret = new JTextField();
        add(txtPret);

        add(new JLabel("  Stoc inițial:"));
        txtStoc = new JTextField();
        add(txtStoc);

        add(new JLabel("  Expiră la (YYYY-MM-DD):"));
        txtDataExpirare = new JTextField();
        add(txtDataExpirare);

        add(new JLabel("  Necesită Rețetă?"));
        chkReteta = new JCheckBox();
        add(chkReteta);

        btnSalveaza = new JButton("Salvează");
        btnAnuleaza = new JButton("Anulează");

        add(btnSalveaza);
        add(btnAnuleaza);

        // Acțiunile butoanelor
        btnAnuleaza.addActionListener(e -> dispose()); // Închide fereastra

        btnSalveaza.addActionListener(e -> salveazaMedicament());
    }

    private void salveazaMedicament() {
        try {
            // Preluăm datele din câmpuri
            String denumire = txtDenumire.getText();
            String categorie = txtCategorie.getText();
            double pret = Double.parseDouble(txtPret.getText());
            int stoc = Integer.parseInt(txtStoc.getText());
            Date dataExpirare = Date.valueOf(txtDataExpirare.getText()); // Conversie din String în java.sql.Date
            boolean necesitaReteta = chkReteta.isSelected();

            // Creăm obiectul Model
            Medicament medicamentNou = new Medicament(0, denumire, categorie, pret, stoc, dataExpirare, necesitaReteta);

            // Îl trimitem către baza de date prin DAO
            MedicamentDAO dao = new MedicamentDAO();
            dao.adaugaMedicament(medicamentNou);

            JOptionPane.showMessageDialog(this, "Medicament adăugat cu succes!");

            // Reîncărcăm tabelul din fundal pentru a afișa noul produs
            parintePanel.incarcaDateInTabel();

            // Închidem fereastra pop-up
            dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Eroare! Verificați dacă ați introdus corect numerele și data (YYYY-MM-DD).", "Eroare de format", JOptionPane.ERROR_MESSAGE);
        }
    }
}