package GUI;

import dao.MedicamentDAO;
import model.Medicament;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class EditeazaMedicamentDialog extends JDialog {

    private JTextField txtDenumire, txtCategorie, txtPret, txtStoc, txtDataExpirare;
    private JCheckBox chkReteta;
    private JButton btnSalveaza, btnAnuleaza;
    private AdminPanel parintePanel;
    private int idMedicament; // Păstrăm ID-ul pentru a ști ce actualizăm

    // Constructorul primește datele rândului selectat pentru a le pune în formular
    public EditeazaMedicamentDialog(JFrame parinteFrame, AdminPanel parintePanel, int id, String denumire, String categorie, double pret, int stoc, Date dataExpirare, boolean necesitaReteta) {
        super(parinteFrame, "Editează Medicament", true);
        this.parintePanel = parintePanel;
        this.idMedicament = id;

        setSize(400, 350);
        setLocationRelativeTo(parinteFrame);
        setLayout(new GridLayout(7, 2, 10, 10));

        // Inițializăm câmpurile direct cu datele primite
        add(new JLabel("  Denumire:"));
        txtDenumire = new JTextField(denumire);
        add(txtDenumire);

        add(new JLabel("  Categorie:"));
        txtCategorie = new JTextField(categorie);
        add(txtCategorie);

        add(new JLabel("  Preț:"));
        txtPret = new JTextField(String.valueOf(pret));
        add(txtPret);

        add(new JLabel("  Stoc:"));
        txtStoc = new JTextField(String.valueOf(stoc));
        add(txtStoc);

        add(new JLabel("  Expiră la (YYYY-MM-DD):"));
        txtDataExpirare = new JTextField(dataExpirare.toString());
        add(txtDataExpirare);

        add(new JLabel("  Necesită Rețetă?"));
        chkReteta = new JCheckBox();
        chkReteta.setSelected(necesitaReteta);
        add(chkReteta);

        btnSalveaza = new JButton("Actualizează");
        btnAnuleaza = new JButton("Anulează");

        add(btnSalveaza);
        add(btnAnuleaza);

        btnAnuleaza.addActionListener(e -> dispose());
        btnSalveaza.addActionListener(e -> actualizeazaMedicament());
    }

    private void actualizeazaMedicament() {
        try {
            String denumire = txtDenumire.getText();
            String categorie = txtCategorie.getText();
            double pret = Double.parseDouble(txtPret.getText());
            int stoc = Integer.parseInt(txtStoc.getText());
            Date dataExpirare = Date.valueOf(txtDataExpirare.getText());
            boolean necesitaReteta = chkReteta.isSelected();

            Medicament medicamentActualizat = new Medicament(idMedicament, denumire, categorie, pret, stoc, dataExpirare, necesitaReteta);

            MedicamentDAO dao = new MedicamentDAO();
            dao.actualizeazaMedicament(medicamentActualizat);

            JOptionPane.showMessageDialog(this, "Medicament actualizat cu succes!");
            parintePanel.incarcaDateInTabel(); // Dăm refresh la tabelul din spate
            dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Verificați datele introduse!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }
}