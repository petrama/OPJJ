package hr.tunjevinac.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum OpcinskiOdbori {

    PODUZETNIŠTVO(1, "Općinski odbor za poduzetništvo"), SPORT(2,
            "Općinski odbor za sport"), KULTURA(3, "Općinski odbor za kulturu"), MLADEŽ(
            4, "Općinski odbor za mladež");

    private String status;
    private Integer id;

    private OpcinskiOdbori(Integer id, String status) {
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
        OpcinskiOdbori[] polje = values();
        for (int i = 0, n = polje.length; i < n; i++) {
            lista.add(polje[i].toString());
        }
        return lista;
    }
}
