package model;

public class DetaliuVanzare {
    private int id;
    private int idVanzare;
    private int idMedicament;
    private int cantitate;
    private double pretLaVanzare;

    public DetaliuVanzare() {}

    public DetaliuVanzare(int id, int idVanzare, int idMedicament, int cantitate, double pretLaVanzare) {
        this.id = id;
        this.idVanzare = idVanzare;
        this.idMedicament = idMedicament;
        this.cantitate = cantitate;
        this.pretLaVanzare = pretLaVanzare;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdVanzare() { return idVanzare; }
    public void setIdVanzare(int idVanzare) { this.idVanzare = idVanzare; }

    public int getIdMedicament() { return idMedicament; }
    public void setIdMedicament(int idMedicament) { this.idMedicament = idMedicament; }

    public int getCantitate() { return cantitate; }
    public void setCantitate(int cantitate) { this.cantitate = cantitate; }

    public double getPretLaVanzare() { return pretLaVanzare; }
    public void setPretLaVanzare(double pretLaVanzare) { this.pretLaVanzare = pretLaVanzare; }
}