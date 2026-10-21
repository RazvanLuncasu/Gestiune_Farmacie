package GUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private AdminPanel adminPanel;
    private FarmacistPanel farmacistPanel;

    public MainFrame() {
        setTitle("Sistem Gestiune Farmacie");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Instanțiem toate panourile
        LoginPanel loginPanel = new LoginPanel(this);
        adminPanel = new AdminPanel(this);
        farmacistPanel = new FarmacistPanel(this);

        // Le adăugăm în CardLayout
        mainPanel.add(loginPanel, "PanouLogin");
        mainPanel.add(adminPanel, "PanouAdmin");
        mainPanel.add(farmacistPanel, "PanouFarmacist");

        // --- LINIA PE CARE AM UITAT-O DATA TRECUTĂ ---
        add(mainPanel);

        // Afișăm automat primul ecran
        cardLayout.show(mainPanel, "PanouLogin");
    }

    public void schimbaEcranul(String numeEcran) {
        cardLayout.show(mainPanel, numeEcran);

        if (numeEcran.equals("PanouAdmin")) {
            adminPanel.incarcaDateInTabel();
        } else if (numeEcran.equals("PanouFarmacist")) {
            farmacistPanel.incarcaStoc();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}