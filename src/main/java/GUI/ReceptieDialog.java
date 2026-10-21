package GUI;

import dao.IntrareMarfaDAO;
import javax.swing.*;
import java.awt.*;

public class ReceptieDialog extends JDialog {

    private JTextField txtFurnizor, txtCantitate;
    private JButton btnSalveaza, btnAnuleaza;

    public ReceptieDialog(JFrame parinteFrame, AdminPanel parintePanel, int idMedicament, String denumireMedicament) {
        super(parinteFrame, "Recepție Marfă: " + denumireMedicament, true);

        setSize(350, 200);
        setLocationRelativeTo(parinteFrame);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("  Nume Furnizor:"));
        txtFurnizor = new JTextField();
        add(txtFurnizor);

        add(new JLabel("  Cantitate Primită:"));
        txtCantitate = new JTextField();
        add(txtCantitate);

        btnSalveaza = new JButton("Înregistrează");
        btnAnuleaza = new JButton("Anulează");

        add(btnSalveaza);
        add(btnAnuleaza);

        btnAnuleaza.addActionListener(e -> dispose());

        btnSalveaza.addActionListener(e -> {
            try {
                String furnizor = txtFurnizor.getText();
                int cantitate = Integer.parseInt(txtCantitate.getText());

                if (furnizor.trim().isEmpty() || cantitate <= 0) {
                    JOptionPane.showMessageDialog(this, "Introduceți un furnizor valid și o cantitate pozitivă!", "Eroare", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                IntrareMarfaDAO dao = new IntrareMarfaDAO();
                boolean succes = dao.inregistreazaIntrare(idMedicament, furnizor, cantitate);

                if (succes) {
                    JOptionPane.showMessageDialog(this, "Stocul a fost actualizat cu succes!");
                    parintePanel.incarcaDateInTabel(); // Dăm refresh la tabelul din spate
                    dispose(); // Închidem fereastra
                } else {
                    JOptionPane.showMessageDialog(this, "A apărut o eroare la salvarea în baza de date.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cantitatea trebuie să fie un număr valid!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}