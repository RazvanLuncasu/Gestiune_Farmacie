package GUI;

import dao.UtilizatorDAO;
import model.Utilizator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private JTextField txtUsername;
    private JPasswordField txtParola;
    private JButton btnLogin;
    private MainFrame parinte; // Referință către fereastra principală pentru a putea schimba ecranele

    public LoginPanel(MainFrame parinte) {
        this.parinte = parinte;

        // Setăm layout-ul panoului (folosim null pentru a poziționa elementele manual, deși în proiecte mari se folosesc Layout Managere)
        setLayout(null);

        // Titlu
        JLabel lblTitlu = new JLabel("Autentificare Sistem Farmacie");
        lblTitlu.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitlu.setBounds(250, 100, 300, 30);
        add(lblTitlu);

        // Etichetă și câmp pentru Username
        JLabel lblUser = new JLabel("Nume utilizator:");
        lblUser.setBounds(250, 180, 100, 25);
        add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(360, 180, 150, 25);
        add(txtUsername);

        // Etichetă și câmp pentru Parolă
        JLabel lblParola = new JLabel("Parola:");
        lblParola.setBounds(250, 230, 100, 25);
        add(lblParola);

        txtParola = new JPasswordField();
        txtParola.setBounds(360, 230, 150, 25);
        add(txtParola);

        // Butonul de Login
        btnLogin = new JButton("Login");
        btnLogin.setBounds(360, 290, 100, 35);
        add(btnLogin);

        // Ce se întâmplă când apăsăm butonul
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificaCredențiale();
            }
        });
    }

    private void verificaCredențiale() {
        String user = txtUsername.getText();
        String pass = new String(txtParola.getPassword()); // JPasswordField returnează un char[], îl transformăm în String

        UtilizatorDAO dao = new UtilizatorDAO();
        Utilizator utilizatorLogat = dao.verificaLogin(user, pass);

        if (utilizatorLogat != null) {
            // Dacă am găsit utilizatorul, verificăm rolul pentru a ști ce ecran îi arătăm
            JOptionPane.showMessageDialog(this, "Autentificare reușită ca " + utilizatorLogat.getRol() + "!");

            if (utilizatorLogat.getRol().equals("ADMIN")) {
                parinte.schimbaEcranul("PanouAdmin");
            } else if (utilizatorLogat.getRol().equals("FARMACIST")) {
                parinte.schimbaEcranul("PanouFarmacist");
            }
        } else {
            // Dacă datele sunt greșite
            JOptionPane.showMessageDialog(this, "Username sau parolă incorecte!", "Eroare Login", JOptionPane.ERROR_MESSAGE);
        }
    }
}