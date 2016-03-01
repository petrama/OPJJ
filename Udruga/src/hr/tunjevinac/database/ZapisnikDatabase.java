package hr.tunjevinac.database;

import hr.tunjevinac.entity.Korisnik;
import hr.tunjevinac.entity.Zapis;

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
public class ZapisnikDatabase implements Callable {

    public static int selector;
    public static Zapis zapis;

    public ZapisnikDatabase() {

    }

    @Override
    public Object call() throws Exception {
        switch (selector) {
        case 1:
            return dohvatiZapise();
        case 2:
            return dodajZapis();
        case 3:
            return obrisiZapis();
        case 4:
            return azurirajZapis();
        case 5:
            return dohvatZadnjegZapisa();
        }
        return null;
    }

    public static List<Zapis> dohvatiZapise() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();
        List<Zapis> zapisi = new ArrayList<>();
        PreparedStatement stmt = con
                .prepareStatement("SELECT * FROM ZAPISNICI.ZAPISNIK ORDER BY DATUM");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Integer id = rs.getInt("ID");
            LocalDateTime datum = rs.getTimestamp("DATUM").toLocalDateTime();
            String naslov = rs.getString("NASLOV");
            String opis = rs.getString("ZAPIS");
            Integer autorId = rs.getInt("AUTOR");

            KorisniciDatabase.id = autorId;
            Korisnik autor = KorisniciDatabase.dohvatiKorisnika();

            Zapis zapis = new Zapis(id, datum, naslov, opis, autor);
            zapisi.add(zapis);
        }

        return zapisi;
    }

    public synchronized static int dodajZapis() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement stmt = con
                .prepareStatement("INSERT INTO ZAPISI.ZAPIS (DATUM, NASLOV, ZAPIS, AUTOR) VALUES (?, ?, ?, ?)");
        stmt.setTimestamp(1, Timestamp.valueOf(zapis.getDatum()));
        stmt.setString(2, zapis.getNaslov());
        stmt.setString(3, zapis.getZapis());
        stmt.setInt(4, zapis.getAutor().getId());
        stmt.executeUpdate();

        ConnectionDatabase.odspajanjeSBaze(con);
        
        return 1;
    }

    public synchronized static int obrisiZapis() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement stmt = con
                .prepareStatement("DELETE FROM ZAPISNICI.ZAPIS WHERE ID = ?");
        stmt.setInt(1, zapis.getId());
        stmt.executeUpdate();

        ConnectionDatabase.odspajanjeSBaze(con);
        
        return 1;
    }

    public synchronized static int azurirajZapis() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement azuriranje = con
                .prepareStatement("UPDATE ZAPISNICI.ZAPIS SET DATUM = ?, NASLOV = ?, ZAPIS = ?, AUTOR = ? WHERE ID=?");
        azuriranje.setTimestamp(1, Timestamp.valueOf(zapis.getDatum()));
        azuriranje.setString(2, zapis.getNaslov());
        azuriranje.setString(3, zapis.getZapis());
        azuriranje.setInt(4, zapis.getAutor().getId());
        azuriranje.setInt(5, zapis.getId());
        azuriranje.executeUpdate();

        ConnectionDatabase.odspajanjeSBaze(con);
        
        return 1;
    }

    public static Zapis dohvatZadnjegZapisa() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement datumTransakcije = con
                .prepareStatement("SELECT * FROM ZAPISNICI.ZAPISNIK ORDER BY DATUM DESC");
        ResultSet rs = datumTransakcije.executeQuery();

        Integer id = rs.getInt("ID");
        LocalDateTime datum = rs.getTimestamp("DATUM").toLocalDateTime();
        String naslov = rs.getString("NASLOV");
        String opis = rs.getString("ZAPIS");
        Integer autorId = rs.getInt("AUTOR");

        KorisniciDatabase.id = autorId;
        Korisnik autor = KorisniciDatabase.dohvatiKorisnika();

        Zapis zapis = new Zapis(id, datum, naslov, opis, autor);

        ConnectionDatabase.odspajanjeSBaze(con);
        return zapis;
    }

}
