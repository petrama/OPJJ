package hr.tunjevinac.entity;

import hr.tunjevinac.application.Main;

import java.time.LocalDateTime;

public class Zapis {

    private Integer id;
    private LocalDateTime datum;
    private String naslov;
    private String zapis;
    private Korisnik autor;

    public Zapis(Integer id, LocalDateTime datum, String naslov, String zapis,
            Korisnik autor) {
        this.id = id;
        this.datum = datum;
        this.naslov = naslov;
        this.zapis = zapis;
        this.autor = autor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public String getDatumS() {
        String datumS = datum.format(Main.formatter);
        return datumS;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getZapis() {
        return zapis;
    }

    public void setZapis(String zapis) {
        this.zapis = zapis;
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
}
