package model;

public class Utilizator {
    private int id;
    private String username;
    private String parola;
    private String rol;

    // Constructor gol (necesar adesea cand citim din baza de date pas cu pas)
    public Utilizator() {
    }

    // Constructor complet (folositor cand cream un utilizator nou din cod)
    public Utilizator(int id, String username, String parola, String rol) {
        this.id = id;
        this.username = username;
        this.parola = parola;
        this.rol = rol;
    }

    // Gettere și Settere pentru a accesa și modifica datele
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getParola() { return parola; }
    public void setParola(String parola) { this.parola = parola; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}