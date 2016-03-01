package hr.tunjevinac.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum Ovlasti {

    ADMINISTRATOR(1, "Administrator"), KORISNIK(2, "Korisnik");

    private String status;
    private int id;

    private Ovlasti(int id, String status) {
        this.status = status;
        this.id = id;
    }

    @Override
    public String toString() {
        return status;
    }

    public int getId() {
        return id;
    }

    static public List<String> valuesString() {
        List<String> lista = new ArrayList<String>();
        Ovlasti[] polje = values();
        for (int i = 0, n = polje.length; i < n; i++) {
            lista.add(polje[i].toString());
            System.out.println(polje[i].toString());
        }
        return lista;
    }
}