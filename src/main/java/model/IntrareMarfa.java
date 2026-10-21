package model;

import java.sql.Timestamp;

public class IntrareMarfa {
    private int id;
    private int idMedicament;
    private String numeFurnizor;
    private int cantitate;
    private Timestamp dataIntrare;

    public IntrareMarfa() {}

    public IntrareMarfa(int id, int idMedicament, String numeFurnizor, int cantitate, Timestamp dataIntrare) {
        this.id = id;
        this.idMedicament = idMedicament;
        this.numeFurnizor = numeFurnizor;
        this.cantitate = cantitate;
        this.dataIntrare = dataIntrare;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdMedicament() { return idMedicament; }
    public void setIdMedicament(int idMedicament) { this.idMedicament = idMedicament; }

    public String getNumeFurnizor() { return numeFurnizor; }
    public void setNumeFurnizor(String numeFurnizor) { this.numeFurnizor = numeFurnizor; }

    public int getCantitate() { return cantitate; }
    public void setCantitate(int cantitate) { this.cantitate = cantitate; }

    public Timestamp getDataIntrare() { return dataIntrare; }
    public void setDataIntrare(Timestamp dataIntrare) { this.dataIntrare = dataIntrare; }
}