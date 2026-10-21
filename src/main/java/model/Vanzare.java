package model;

import java.sql.Timestamp;

public class Vanzare {
    private int id;
    private Timestamp dataVanzare;
    private int idUtilizator;
    private int idClient;

    public Vanzare() {}

    public Vanzare(int id, Timestamp dataVanzare, int idUtilizator, int idClient) {
        this.id = id;
        this.dataVanzare = dataVanzare;
        this.idUtilizator = idUtilizator;
        this.idClient = idClient;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Timestamp getDataVanzare() { return dataVanzare; }
    public void setDataVanzare(Timestamp dataVanzare) { this.dataVanzare = dataVanzare; }

    public int getIdUtilizator() { return idUtilizator; }
    public void setIdUtilizator(int idUtilizator) { this.idUtilizator = idUtilizator; }

    public int getIdClient() { return idClient; }
    public void setIdClient(int idClient) { this.idClient = idClient; }
}