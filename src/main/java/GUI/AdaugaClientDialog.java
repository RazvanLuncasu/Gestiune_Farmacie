package GUI;

import dao.ClientDAO;
import model.Client;

import javax.swing.*;
import java.awt.*;

public class AdaugaClientDialog extends JDialog {

    private JTextField txtNume, txtPrenume, txtTelefon;
    private JButton btnSalveaza, btnAnuleaza;
    private FarmacistPanel parintePanel;

    public AdaugaClientDialog(JFrame parinteFrame, FarmacistPanel parintePanel) {
        super(parinteFrame, "Înregistrare Client Nou", true);
        this.parintePanel = parintePanel;

        setSize(320, 220);
        setLocationRelativeTo(parinteFrame);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("  Nume:"));
        txtNume = new JTextField();
        add(txtNume);

        add(new JLabel("  Prenume:"));
        txtPrenume = new JTextField();
        add(txtPrenume);

        add(new JLabel("  Telefon:"));
        txtTelefon = new JTextField();
        add(txtTelefon);

        btnSalveaza = new JButton("Înregistrează");
        btnAnuleaza = new JButton("Anulează");

        add(btnSalveaza);
        add(btnAnuleaza);

        btnAnuleaza.addActionListener(e -> dispose());

        btnSalveaza.addActionListener(e -> {
            String nume = txtNume.getText().trim();
            String prenume = txtPrenume.getText().trim();
            String telefon = txtTelefon.getText().trim();

            if (nume.isEmpty() || prenume.isEmpty() || telefon.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Toate câmpurile sunt obligatorii!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Client clientNou = new Client(0, nume, prenume, telefon);
            ClientDAO dao = new ClientDAO();
            boolean succes = dao.adaugaClient(clientNou);

            if (succes) {
                JOptionPane.showMessageDialog(this, "Clientul a fost înregistrat cu succes!");
                parintePanel.incarcaClienti(); // Dăm refresh la lista derulantă ca să apară imediat!
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Eroare! Este posibil ca acest număr de telefon să fie deja înregistrat.", "Eroare Bază de Date", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}