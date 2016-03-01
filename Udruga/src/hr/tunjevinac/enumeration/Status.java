package hr.tunjevinac.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum Status {

    PREDSJEDNIK(1, "Predsjednik"), POTPREDSJEDNIK(2, "Potpredsjednik"), TAJNIK(
            3, "Tajnik"), BLAGAJNIK(4, "Blagajnik"), CLAN(5, "Član"), NIJE_CLAN(
            6, "Nije član");

    private String status;
    private Integer id;

    private Status(Integer id, String status) {
        this.status = status;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    static public List<String> valuesString() {
        List<String> lista = new ArrayList<String>();
        Status[] polje = values();
        for (int i = 0, n = polje.length; i < n; i++) {
            lista.add(polje[i].toString());
        }
        return lista;
    }
}
