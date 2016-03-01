package hr.tunjevinac.database;

import hr.tunjevinac.entity.Clan;
import hr.tunjevinac.enumeration.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings("rawtypes")
public class ClanoviDatabase implements Callable {

    public static int selector;
    public static Clan clan;

    public ClanoviDatabase() {
    }

    @Override
    public Object call() throws Exception {
        switch (selector) {
        case 1:
            return dohvatiClanove();
        case 2:
            return dodajClana();
        case 3:
            return obrisiClana();
        case 4:
            return azurirajClana();
        case 5:
            return brojClanova();
        }
        return null;
    }

    public static List<Clan> dohvatiClanove() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();
        List<Clan> clanovi = new ArrayList<>();
        PreparedStatement stmt = con
                .prepareStatement("SELECT * FROM CLANOVI.CLAN");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Integer id = rs.getInt("ID");
            String ime = rs.getString("IME");
            String prezime = rs.getString("PREZIME");
            String oib = rs.getString("OIB");
            String kontBroj = rs.getString("KONTAKT_BROJ");
            String email = rs.getString("EMAIL");
            Integer statusId = rs.getInt("STATUS");

            Status status = null;
            for (Status temp : Status.values()) {
                if (temp.getId() == statusId) {
                    status = temp;
                }
            }

            Clan clan = new Clan(id, ime, prezime, oib, kontBroj, email, status);
            clanovi.add(clan);
        }

        return clanovi;
    }

    public synchronized static int dodajClana() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement stmt = con
                .prepareStatement("INSERT INTO CLANOVI.CLAN (IME, PREZIME, OIB, KONTAKT_BROJ, EMAIL, STATUS) VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setString(1, clan.getIme());
        stmt.setString(2, clan.getPrezime());
        stmt.setString(3, clan.getOib());
        stmt.setString(4, clan.getKontaktBroj());
        stmt.setString(5, clan.getEmail());
        stmt.setInt(6, clan.getStatus().getId());
        stmt.executeUpdate();

        ConnectionDatabase.odspajanjeSBaze(con);

        return 1;
    }

    public synchronized static int obrisiClana() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement stmt = con
                .prepareStatement("DELETE FROM CLANOVI.CLAN WHERE ID = ?");
        stmt.setInt(1, clan.getId());
        stmt.executeUpdate();

        ConnectionDatabase.odspajanjeSBaze(con);

        return 1;
    }

    public synchronized static int azurirajClana() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement azuriranje = con
                .prepareStatement("UPDATE ZAPISNICI.ZAPIS SET DATUM = ?, NASLOV = ?, ZAPIS = ?, AUTOR = ? WHERE ID=?");
        azuriranje.setString(1, clan.getIme());
        azuriranje.setString(2, clan.getPrezime());
        azuriranje.setString(3, clan.getOib());
        azuriranje.setString(4, clan.getKontaktBroj());
        azuriranje.setString(5, clan.getEmail());
        azuriranje.setInt(6, clan.getStatus().getId());
        azuriranje.setInt(7, clan.getId());
        azuriranje.executeUpdate();

        ConnectionDatabase.odspajanjeSBaze(con);

        return 1;
    }
    
    public static Integer brojClanova() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();
        PreparedStatement stmt = con
                .prepareStatement("SELECT COUNT(*) AS BROJ FROM CLANOVI.CLAN");
        ResultSet rs = stmt.executeQuery();
        
        Integer broj = null;
        while (rs.next()) {
            broj = rs.getInt("broj");
        }

        return broj;
    }
}
