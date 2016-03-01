package hr.tunjevinac.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum Ogranak {

    KLINČA_SELA(1, "Klinča sela"), OKIĆ(2, "Okić"), KUPINEC(3, "Kupinec"), GORNJA_ZDENČINA(
            4, "Gornja Zdenčina"), DONJA_ZDENČINA(5, "Donja Zdenčina"), ZDENČINA(
            6, "Zdenčina centar");

    private String status;
    private Integer id;

    private Ogranak(Integer id, String status) {
        this.status = status;
        this.id = id;
    }

    @Override
    public String toString() {
        return status;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    static public List<String> valuesString() {
        List<String> lista = new ArrayList<String>();
        Ogranak[] polje = values();
        for (int i = 0, n = polje.length; i < n; i++) {
            lista.add(polje[i].toString());
        }
        return lista;
    }
}