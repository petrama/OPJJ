package hr.tunjevinac.entity;

import hr.tunjevinac.enumeration.Status;

public class Clan {

    private String ime;
    private String prezime;
    private String oib;
    private String kontaktBroj;
    private String email;
    private Status status;
    private Integer id;

    public Clan(Integer id, String ime, String prezime, String oib,
            String kontaktBroj, String email, Status status) {
        super();
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.oib = oib;
        this.kontaktBroj = kontaktBroj;
        this.email = email;
        this.status = status;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKontaktBroj() {
        return kontaktBroj;
    }

    public void setKontaktBroj(String kontaktBroj) {
        this.kontaktBroj = kontaktBroj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
