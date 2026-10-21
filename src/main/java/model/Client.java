package model;

public class Client {
    private int id;
    private String nume;
    private String prenume;
    private String telefon;

    public Client() {}

    public Client(int id, String nume, String prenume, String telefon) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.telefon = telefon;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public String getPrenume() { return prenume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    // Metodă adăugată pentru a afișa frumos clientul în interfața grafică
    @Override
    public String toString() {
        if (id == 0) return "Client Standard (Fără înregistrare)";
        return nume + " " + prenume + " - " + telefon;
    }
}