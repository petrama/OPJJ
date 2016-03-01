package hr.tunjevinac.entity;

import hr.tunjevinac.enumeration.MjesniOdbori;
import hr.tunjevinac.enumeration.Ogranak;
import hr.tunjevinac.enumeration.OpcinskiOdbori;
import hr.tunjevinac.enumeration.Status;

import java.util.ArrayList;
import java.util.List;

public class Polozaj {

    private OpcinskaOrganizacija opcina;
    private MjesnaOrganizacija mjesto;
    private Integer id;

    public Polozaj(Integer id, OpcinskaOrganizacija opcina,
            MjesnaOrganizacija mjesto) {
        this.id = id;
        this.opcina = opcina;
        this.mjesto = mjesto;
    }

    public OpcinskaOrganizacija getOpcina() {
        return opcina;
    }

    public void setOpcina(OpcinskaOrganizacija opcina) {
        this.opcina = opcina;
    }

    public MjesnaOrganizacija getMjesto() {
        return mjesto;
    }

    public void setMjesto(MjesnaOrganizacija mjesto) {
        this.mjesto = mjesto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public class MjesniOdbor {
        private Status status;
        private MjesniOdbori mjesniOdbori;
        private Integer id;

        public MjesniOdbor(Integer id, Status status, MjesniOdbori mjesniOdbori) {
            this.id = id;
            this.status = status;
            this.mjesniOdbori = mjesniOdbori;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public MjesniOdbori getMjesniOdbori() {
            return mjesniOdbori;
        }

        public void setMjesniOdbori(MjesniOdbori mjesniOdbori) {
            this.mjesniOdbori = mjesniOdbori;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

    public class OpcinskaOrganizacija {
        private Status status;
        private List<OpcinskiOdbori> opcinskiOdbori = new ArrayList<>();
        private boolean vijece;
        private Integer id;

        public OpcinskaOrganizacija(Integer id, Status status,
                OpcinskiOdbori opcinskiOdbori, boolean vijece) {
            this.id = id;
            this.status = status;
            this.opcinskiOdbori.add(opcinskiOdbori);
            this.vijece = vijece;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public List<OpcinskiOdbori> getOpcinskiOdbori() {
            return opcinskiOdbori;
        }

        public void setOpcinskiOdbori(List<OpcinskiOdbori> opcinskiOdbori) {
            this.opcinskiOdbori = opcinskiOdbori;
        }

        public boolean isVijece() {
            return vijece;
        }

        public void setVijece(boolean vijece) {
            this.vijece = vijece;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

    public class MjesnaOrganizacija {
        private MjesniOdbor mjesniOdbor;
        private Status status;
        private Ogranak ogranak;
        private Integer id;

        public MjesnaOrganizacija(Integer id, MjesniOdbor mjesniOdbor,
                Status status, Ogranak ogranak) {
            this.id = id;
            this.mjesniOdbor = mjesniOdbor;
            this.status = status;
            this.ogranak = ogranak;
        }

        public MjesniOdbor getMjesniOdbor() {
            return mjesniOdbor;
        }

        public void setMjesniOdbor(MjesniOdbor mjesniOdbor) {
            this.mjesniOdbor = mjesniOdbor;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Ogranak getOgranak() {
            return ogranak;
        }

        public void setOgranak(Ogranak ogranak) {
            this.ogranak = ogranak;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
