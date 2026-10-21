package model;

import java.sql.Date;

public class Medicament {
    private int id;
    private String denumire;
    private String categorie;
    private double pret;
    private int stoc;
    private Date dataExpirare;
    private boolean necesitaReteta;

    public Medicament() {
    }

    public Medicament(int id, String denumire, String categorie, double pret, int stoc, Date dataExpirare, boolean necesitaReteta) {
        this.id = id;
        this.denumire = denumire;
        this.categorie = categorie;
        this.pret = pret;
        this.stoc = stoc;
        this.dataExpirare = dataExpirare;
        this.necesitaReteta = necesitaReteta;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDenumire() { return denumire; }
    public void setDenumire(String denumire) { this.denumire = denumire; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public double getPret() { return pret; }
    public void setPret(double pret) { this.pret = pret; }

    public int getStoc() { return stoc; }
    public void setStoc(int stoc) { this.stoc = stoc; }

    public Date getDataExpirare() { return dataExpirare; }
    public void setDataExpirare(Date dataExpirare) { this.dataExpirare = dataExpirare; }

    public boolean isNecesitaReteta() { return necesitaReteta; }
    public void setNecesitaReteta(boolean necesitaReteta) { this.necesitaReteta = necesitaReteta; }
}