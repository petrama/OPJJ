package hr.tunjevinac.entity;

import hr.tunjevinac.application.Main;
import hr.tunjevinac.enumeration.Ovlasti;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class Korisnik {

    /**
     * 
     */
    public static Korisnik trenutniKorisnik;
    private String korisnickoIme;
    private String lozinka;
    private String ime;
    private String prezime;
    private Ovlasti ovlasti;
    private Integer id;
    private LocalDateTime aktivnost;

    public Korisnik() {
    }

    public Korisnik(Integer id, String korisnickoIme, String lozinka,
            String ime, String prezime, Ovlasti ovlasti, LocalDateTime aktivnost) {
        super();
        this.id = id;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.ovlasti = ovlasti;
        this.aktivnost = aktivnost;
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

    public LocalDateTime getAktivnost() {
        return aktivnost;
    }

    public String getAktivnostS() {
        return aktivnost.format(Main.formatter);
    }

    public void setAktivnost(LocalDateTime aktivnost) {
        this.aktivnost = aktivnost;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public Ovlasti getOvlasti() {
        return ovlasti;
    }

    public void setOvlasti(Ovlasti ovlasti) {
        this.ovlasti = ovlasti;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static String computeHash(String x) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(x.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }

        return sb.toString();
    }

}
