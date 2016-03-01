package hr.tunjevinac.database;

import hr.tunjevinac.entity.Dogadaj;
import hr.tunjevinac.entity.Korisnik;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings("rawtypes")
public class KalendarDatabase implements Callable {

    public static Integer selector;
    public static Dogadaj dogadaj;
    
    public KalendarDatabase() {
    }
    
    @Override
    public Object call() throws Exception {
        switch (selector) {
        case 1:
            return dohvatiDogadaje();
        case 2:
            return dodajDogadaj();
        case 3:
            return obrisiDogadaj();
        case 4:
            return azurirajKalendar();
        case 5:
            return dohvatZadnjegDogadaja();
        }
        return null;
    }
    
    public static List<Dogadaj> dohvatiDogadaje() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();
        List<Dogadaj> dogadaji = new ArrayList<>();
        PreparedStatement stmt = con
                .prepareStatement("SELECT * FROM KALENDAR.DOGADAJ ORDER BY DATUM");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Integer id = rs.getInt("ID");
            LocalDateTime datum = rs.getTimestamp("DATUM").toLocalDateTime();
            String naslov = rs.getString("NASLOV");
            String mjesto = rs.getString("MJESTO");
            String opis = rs.getString("OPIS");
            Integer autorId = rs.getInt("AUTOR");

            KorisniciDatabase.id = autorId;
            Korisnik autor = KorisniciDatabase.dohvatiKorisnika();

            Dogadaj dogadaj = new Dogadaj(id, datum, naslov, opis, mjesto,
                    autor);
            dogadaji.add(dogadaj);
        }

        return dogadaji;
    }

    public synchronized static int dodajDogadaj() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement stmt = con
                .prepareStatement("INSERT INTO KALENDAR.DOGADAJ (DATUM, NASLOV, OPIS, MJESTO, AUTOR) VALUES (?, ?, ?, ?, ?)");
        stmt.setTimestamp(1, Timestamp.valueOf(dogadaj.getDatum()));
        stmt.setString(2, dogadaj.getNaslov());
        stmt.setString(3, dogadaj.getOpis());
        stmt.setString(4, dogadaj.getMjesto());
        stmt.setInt(5, dogadaj.getAutor().getId());
        stmt.executeUpdate();

        ConnectionDatabase.odspajanjeSBaze(con);
        
        return 1;
    }

    public synchronized static int obrisiDogadaj() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement stmt = con
                .prepareStatement("DELETE FROM KALENDAR.DOGADAJ WHERE ID = ?");
        stmt.setInt(1, dogadaj.getId());
        stmt.executeUpdate();

        ConnectionDatabase.odspajanjeSBaze(con);
        
        return 1;
    }

    public synchronized static int azurirajKalendar() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement azuriranje = con
                .prepareStatement("UPDATE ZAPISNICI.ZAPIS SET DATUM = ?, NASLOV = ?, OPIS = ?, MJESTO = ?, AUTOR = ? WHERE ID=?");
        azuriranje.setTimestamp(1, Timestamp.valueOf(dogadaj.getDatum()));
        azuriranje.setString(2, dogadaj.getNaslov());
        azuriranje.setString(3, dogadaj.getOpis());
        azuriranje.setString(4, dogadaj.getMjesto());
        azuriranje.setInt(5, dogadaj.getAutor().getId());
        azuriranje.setInt(6, dogadaj.getId());
        azuriranje.executeUpdate();

        ConnectionDatabase.odspajanjeSBaze(con);
        
        return 1;
    }

    public static Dogadaj dohvatZadnjegDogadaja() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement datumTransakcije = con
                .prepareStatement("SELECT * FROM KALENDAR.DOGADAJ ORDER BY DATUM");
        ResultSet rs = datumTransakcije.executeQuery();

        Dogadaj dogadaj = null;
        while (rs.next()) {
            Integer id = rs.getInt("ID");
            LocalDateTime datum = rs.getTimestamp("DATUM").toLocalDateTime();
            String naslov = rs.getString("NASLOV");
            String mjesto = rs.getString("MJESTO");
            String opis = rs.getString("OPIS");
            Integer autorId = rs.getInt("AUTOR");

            KorisniciDatabase.id = autorId;
            Korisnik autor = KorisniciDatabase.dohvatiKorisnika();

            dogadaj = new Dogadaj(id, datum, naslov, opis, mjesto, autor);
        }
        ConnectionDatabase.odspajanjeSBaze(con);
        return dogadaj;
    }


}
