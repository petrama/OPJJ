package hr.tunjevinac.database;

import hr.tunjevinac.entity.Korisnik;
import hr.tunjevinac.entity.Transakcija;

import java.math.BigDecimal;
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
public class BlagajnaDatabase implements Callable {
    
    public static int selector;
    public static Transakcija transakcija;

    public BlagajnaDatabase() {
    }

    @Override
    public Object call() throws Exception {
        switch (selector) {
        case 1:
            return dohvatTransakcija();
        case 2:
            return dodajTransakciju();
        case 3:
            return obrisiTransakciju();
        case 4:
            return azurirajBlagajnu();
        case 5:
            return dohvatZadnjeTransakcije();
        }
        return null;
    }
    
    public static List<Transakcija> dohvatTransakcija() throws SQLException {
        List<Transakcija> transakcije = new ArrayList<>();
        Transakcija transakcija;
        Connection connection = ConnectionDatabase.spajanjeNaBazu();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM BLAGAJNA.TRANSAKCIJA ORDER BY DATUM");
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            Integer id = rs.getInt("ID");
            LocalDateTime datum = rs.getTimestamp("DATUM").toLocalDateTime();
            String opis = rs.getString("OPIS");
            String iban = rs.getString("IBAN");
            BigDecimal iznos = rs.getBigDecimal("IZNOS");
            BigDecimal saldo = rs.getBigDecimal("SALDO");
            Integer autorId = rs.getInt("AUTOR");
            
            KorisniciDatabase.id = autorId;
            Korisnik korisnik = KorisniciDatabase.dohvatiKorisnika();
            transakcija = new Transakcija(id, datum, opis, iban, iznos, saldo,
                    korisnik);
            transakcije.add(transakcija);
        }

        ConnectionDatabase.odspajanjeSBaze(connection);
        return transakcije;
    }

    public synchronized static int dodajTransakciju()
            throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement stmt = con
                .prepareStatement("INSERT INTO BLAGAJNA.TRANSAKCIJA (DATUM, IZNOS, SALDO, OPIS, IBAN, AUTOR) VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setTimestamp(1, Timestamp.valueOf(transakcija.getDatum()));
        stmt.setBigDecimal(2, transakcija.getIznos());
        stmt.setBigDecimal(3, transakcija.getSaldo());
        stmt.setString(4, transakcija.getOpis());
        stmt.setString(5, transakcija.getIban());
        stmt.setInt(6, transakcija.getAutor().getId());
        stmt.executeUpdate();

        ConnectionDatabase.odspajanjeSBaze(con);
        
        return 1;
    }

    public synchronized static int obrisiTransakciju()
            throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement stmt = con
                .prepareStatement("DELETE FROM BLAGAJNA.TRANSAKCIJA WHERE ID = ?");
        stmt.setInt(1, transakcija.getId());
        stmt.executeUpdate();

        ConnectionDatabase.odspajanjeSBaze(con);
        
        return 1;
    }

    public static Transakcija dohvatZadnjeTransakcije() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement iznosSalda = con
                .prepareStatement("SELECT SALDO FROM BLAGAJNA.TRANSAKCIJA ORDER BY DATUM");
        ResultSet rs = iznosSalda.executeQuery();

        Transakcija transakcija = null;
        while (rs.next()) {
            Integer id = rs.getInt("ID");
            LocalDateTime datum = rs.getTimestamp("DATUM").toLocalDateTime();
            String opis = rs.getString("OPIS");
            String iban = rs.getString("IBAN");
            BigDecimal iznos = rs.getBigDecimal("IZNOS");
            BigDecimal saldo = rs.getBigDecimal("SALDO");
            Integer autorId = rs.getInt("AUTOR");
KorisniciDatabase.id = autorId;
            Korisnik korisnik = KorisniciDatabase.dohvatiKorisnika();
            transakcija = new Transakcija(id, datum, opis, iban, iznos, saldo,
                    korisnik);
        }
        ConnectionDatabase.odspajanjeSBaze(con);
        return transakcija;
    }

    public synchronized static int azurirajBlagajnu()
            throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement azuriranje = con
                .prepareStatement("UPDATE BLAGAJNA.TRANSAKCIJA SET DATUM = ?, SALDO = ?, IZNOS = ?, IBAN = ?, OPIS = ?, AUTOR = ? WHERE ID=?");
        azuriranje.setTimestamp(1, Timestamp.valueOf(transakcija.getDatum()));
        azuriranje.setBigDecimal(2, transakcija.getSaldo());
        azuriranje.setBigDecimal(3, transakcija.getIznos());
        azuriranje.setString(4, transakcija.getIban());
        azuriranje.setString(5, transakcija.getOpis());
        azuriranje.setInt(6, transakcija.getAutor().getId());
        azuriranje.setInt(7, transakcija.getId());
        azuriranje.executeUpdate();

        ConnectionDatabase.odspajanjeSBaze(con);
        
        return 1;
    }

}
