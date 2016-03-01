package hr.tunjevinac.entity;

import hr.tunjevinac.application.Main;

import java.time.LocalDateTime;

public class Dogadaj {

    private String naslov;
    private String opis;
    private LocalDateTime datum;
    private Integer id;
    private Korisnik autor;
    private String mjesto;

    public Dogadaj(Integer id, LocalDateTime datum, String naslov, String opis,
            String mjesto, Korisnik autor) {
        super();
        this.naslov = naslov;
        this.opis = opis;
        this.datum = datum;
        this.id = id;
        this.autor = autor;
        this.mjesto = mjesto;
    }

    public String getMjesto() {
        return mjesto;
    }

    public void setMjesto(String mjesto) {
        this.mjesto = mjesto;
    }

    public String getNaslov() {
        return naslov;
    }

    public Korisnik getAutor() {
        return autor;
    }

    public void setAutor(Korisnik autor) {
        this.autor = autor;
    }

    public String getAutorS() {
        String autorS = autor.getIme() + " " + autor.getPrezime();
        return autorS;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public String getDatumS() {
        String datumS = datum.format(Main.formatter);
        return datumS;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}