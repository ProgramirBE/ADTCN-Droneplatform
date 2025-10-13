package be.odisee.Team1.Citymesh.legacy.model;

public class Onderdeel {

    private int onderdeelId;
    private String naam;
    private int voorraad;

    public int getOnderdeelId() {
        return onderdeelId;
    }

    public void setOnderdeelId(int onderdeelId) {
        this.onderdeelId = onderdeelId;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getVoorraad() {
        return voorraad;
    }

    public void setVoorraad(int voorraad) {
        this.voorraad = voorraad;
    }
}
