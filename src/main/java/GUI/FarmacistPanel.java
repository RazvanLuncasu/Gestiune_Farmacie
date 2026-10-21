package GUI;

import dao.ClientDAO;
import dao.MedicamentDAO;
import dao.VanzareDAO;
import model.Client;
import model.Medicament;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FarmacistPanel extends JPanel {

    private MainFrame parinte;
    private DefaultTableModel modelStoc, modelCos;
    private JTable tabelStoc, tabelCos;
    private JLabel lblTotal;
    private JComboBox<Client> comboClienti;
    private double totalCos = 0;
    private List<Medicament> medicamenteInCos = new ArrayList<>();

    public FarmacistPanel(MainFrame parinte) {
        this.parinte = parinte;
        setLayout(new BorderLayout(10, 10));

        // --- 1. PARTEA DE SUS: Titlu și Delogare ---
        JPanel panouSus = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblTitlu = new JLabel("Panou Farmacist - Casă de Marcat");
        lblTitlu.setFont(new Font("Arial", Font.BOLD, 18));
        JButton btnLogout = new JButton("Delogare");
        btnLogout.addActionListener(e -> parinte.schimbaEcranul("PanouLogin"));

        panouSus.add(lblTitlu);
        panouSus.add(Box.createHorizontalStrut(200));
        panouSus.add(btnLogout);
        add(panouSus, BorderLayout.NORTH);

        // --- 2. PARTEA CENTRALĂ: Tabelele ---
        JPanel panouCentru = new JPanel(new GridLayout(2, 1, 10, 10));

        // Tabelul de sus (Stoc Disponibil)
        String[] coloaneStoc = {"ID", "Denumire", "Categorie", "Preț", "Stoc", "Rețetă"};
        modelStoc = new DefaultTableModel(coloaneStoc, 0);
        tabelStoc = new JTable(modelStoc);

        JPanel panouStoc = new JPanel(new BorderLayout());
        panouStoc.add(new JLabel("Stoc Disponibil (Selectați pentru a adăuga)"), BorderLayout.NORTH);
        panouStoc.add(new JScrollPane(tabelStoc), BorderLayout.CENTER);
        panouCentru.add(panouStoc);

        // Tabelul de jos (Coș de cumpărături)
        String[] coloaneCos = {"ID", "Denumire", "Cantitate", "Preț Total"};
        modelCos = new DefaultTableModel(coloaneCos, 0);
        tabelCos = new JTable(modelCos);

        JPanel panouCos = new JPanel(new BorderLayout());
        panouCos.add(new JLabel("Coș de Cumpărături Curent"), BorderLayout.NORTH);
        panouCos.add(new JScrollPane(tabelCos), BorderLayout.CENTER);
        panouCentru.add(panouCos);

        add(panouCentru, BorderLayout.CENTER);

        // --- 3. PARTEA DE JOS: Acțiuni, Clienți și Total ---
        // Folosim GridLayout pe 2 rânduri ca să avem spațiu berechet
        JPanel panouJos = new JPanel(new GridLayout(2, 1));

        JButton btnAdaugaCos = new JButton("Adaugă în Coș");
        JButton btnGolesteCos = new JButton("Golește Coș");
        JButton btnFinalizeaza = new JButton("Finalizează Vânzarea");

        // --- Buton pentru Client Nou ---
        JButton btnClientNou = new JButton("+");
        btnClientNou.setToolTipText("Înregistrează un client nou");

        comboClienti = new JComboBox<>();
        incarcaClienti();

        lblTotal = new JLabel("Total: 0.00 RON");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setForeground(Color.RED);

        // Rândul 1 (Sus): Selecție Client și Total
        JPanel rand1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        rand1.add(new JLabel("Selectare Client:"));
        rand1.add(comboClienti);
        rand1.add(btnClientNou);
        rand1.add(Box.createHorizontalStrut(50)); // Spațiu mare între client și total
        rand1.add(lblTotal);

        // Rândul 2 (Jos): Butoanele de coș
        JPanel rand2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        rand2.add(btnAdaugaCos);
        rand2.add(btnGolesteCos);
        rand2.add(btnFinalizeaza);

        panouJos.add(rand1);
        panouJos.add(rand2);

        add(panouJos, BorderLayout.SOUTH);

        incarcaStoc();

        // --- ACȚIUNI BUTOANE ---
        btnClientNou.addActionListener(e -> {
            AdaugaClientDialog dialog = new AdaugaClientDialog(parinte, this);
            dialog.setVisible(true);
        });

        btnAdaugaCos.addActionListener(e -> {
            int rand = tabelStoc.getSelectedRow();
            if (rand == -1) {
                JOptionPane.showMessageDialog(this, "Selectați un medicament din tabelul de stoc superior!");
                return;
            }

            int id = (int) modelStoc.getValueAt(rand, 0);
            String denumire = (String) modelStoc.getValueAt(rand, 1);
            double pret = (double) modelStoc.getValueAt(rand, 3);
            int stocDisponibil = (int) modelStoc.getValueAt(rand, 4);

            if (stocDisponibil <= 0) {
                JOptionPane.showMessageDialog(this, "Stoc epuizat pentru acest produs!");
                return;
            }

            String cantitateStr = JOptionPane.showInputDialog(this, "Introduceți cantitatea dorită (Maxim " + stocDisponibil + "):");
            if (cantitateStr != null && !cantitateStr.isEmpty()) {
                try {
                    int cantitate = Integer.parseInt(cantitateStr);
                    if (cantitate > 0 && cantitate <= stocDisponibil) {

                        Medicament produsCos = new Medicament();
                        produsCos.setId(id);
                        produsCos.setDenumire(denumire);
                        produsCos.setPret(pret);
                        produsCos.setStoc(cantitate);

                        medicamenteInCos.add(produsCos);
                        double pretTotalProdus = pret * cantitate;
                        modelCos.addRow(new Object[]{id, denumire, cantitate, pretTotalProdus});

                        totalCos += pretTotalProdus;
                        actualizeazaTotal();
                    } else {
                        JOptionPane.showMessageDialog(this, "Cantitate invalidă sau stoc insuficient!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Vă rugăm să introduceți un număr valid!");
                }
            }
        });

        btnGolesteCos.addActionListener(e -> golesteCos());

        btnFinalizeaza.addActionListener(e -> {
            if (medicamenteInCos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Coșul este gol!");
                return;
            }

            Client clientSelectat = (Client) comboClienti.getSelectedItem();
            int idClient = (clientSelectat != null) ? clientSelectat.getId() : 0;

            VanzareDAO dao = new VanzareDAO();
            boolean succes = dao.inregistreazaVanzare(2, idClient, medicamenteInCos);

            if (succes) {
                JOptionPane.showMessageDialog(this, "Vânzare finalizată cu succes!");
                golesteCos();
                incarcaStoc();
                comboClienti.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "Eroare la finalizarea vânzării!");
            }
        });
    }

    public void incarcaClienti() {
        comboClienti.removeAllItems();

        Client clientStandard = new Client(0, "Client Standard", "(Fără înregistrare)", "");
        comboClienti.addItem(clientStandard);

        ClientDAO dao = new ClientDAO();
        List<Client> clientiBD = dao.getTotiClientii();
        for (Client c : clientiBD) {
            comboClienti.addItem(c);
        }

        // Dacă am apelat metoda pentru a da refresh, e drăguț să selectăm automat ultimul client adăugat
        if (comboClienti.getItemCount() > 1) {
            comboClienti.setSelectedIndex(comboClienti.getItemCount() - 1);
        }
    }

    public void incarcaStoc() {
        modelStoc.setRowCount(0);
        MedicamentDAO dao = new MedicamentDAO();
        List<Medicament> medicamente = dao.getToateMedicamentele();
        for (Medicament m : medicamente) {
            modelStoc.addRow(new Object[]{m.getId(), m.getDenumire(), m.getCategorie(), m.getPret(), m.getStoc(), m.isNecesitaReteta() ? "Da" : "Nu"});
        }
    }

    private void golesteCos() {
        medicamenteInCos.clear();
        modelCos.setRowCount(0);
        totalCos = 0;
        actualizeazaTotal();
    }

    private void actualizeazaTotal() {
        lblTotal.setText("Total: " + String.format("%.2f", totalCos) + " RON");
    }
}