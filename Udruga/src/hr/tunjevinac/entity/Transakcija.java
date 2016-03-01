package hr.tunjevinac.entity;

import hr.tunjevinac.application.Main;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transakcija {

    private Integer id;
    private LocalDateTime datum;
    private String opis;
    private String iban;
    private BigDecimal iznos;
    private BigDecimal saldo;
    private Korisnik autor;

    public Transakcija(Integer id, LocalDateTime datum, String opis,
            String iban, BigDecimal iznos, BigDecimal saldo, Korisnik autor) {
        this.id = id;
        this.datum = datum;
        this.iznos = iznos;
        this.saldo = saldo;
        this.opis = opis;
        this.iban = iban;
        this.autor = autor;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public String getDatumS() {
        return datum.format(Main.formatter);
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public BigDecimal getIznos() {
        return iznos;
    }

    public void setIznos(BigDecimal iznos) {
        this.iznos = iznos;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

}
