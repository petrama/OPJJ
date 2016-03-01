package hr.tunjevinac.database;

import hr.tunjevinac.entity.Korisnik;
import hr.tunjevinac.enumeration.Ovlasti;

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
public class KorisniciDatabase implements Callable {
    
    public static Integer selector;
    public static Integer id;
    public static Korisnik korisnik;
    
    public KorisniciDatabase() {
        
    } 
    
    @Override
    public Object call() throws Exception {
        switch (selector) {
        case 1:
            return dohvatiKorisnike();
        case 2:
            return dodajKorisnika();
        case 3:
            return obrisiKorisnika();
        case 4:
            return azurirajKorisnika();
        case 5: 
            return dohvatiKorisnika();
        case 6:
            return azurirajAktivnostKorisnika();
        }
        return null;
    }

    public static List<Korisnik> dohvatiKorisnike() throws SQLException {
        List<Korisnik> korisnici = new ArrayList<>();
        Korisnik korisnik;
        Connection connection = ConnectionDatabase.spajanjeNaBazu();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM KORISNICI.KORISNIK ORDER BY AKTIVNOST");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Integer id = rs.getInt("ID");
            String korisnickoIme = rs.getString("KOR_IME");
            String lozinka = rs.getString("LOZINKA");
            String ime = rs.getString("IME");
            String prezime = rs.getString("PREZIME");
            Integer ovlastId = rs.getInt("OVLASTI");
            LocalDateTime aktivnost = rs.getTimestamp("AKTIVNOST")
                    .toLocalDateTime();

            Ovlasti ovlasti = null;
            for (Ovlasti temp : Ovlasti.values()) {
                if (temp.getId() == ovlastId) {
                    ovlasti = temp;
                }
            }

            korisnik = new Korisnik(id, korisnickoIme, lozinka, ime, prezime,
                    ovlasti, aktivnost);
            korisnici.add(korisnik);
        }

        ConnectionDatabase.odspajanjeSBaze(connection);
        return korisnici;
    }

    public static Korisnik dohvatiKorisnika() throws SQLException {
        Korisnik korisnik = null;
        Connection connection = ConnectionDatabase.spajanjeNaBazu();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM KORISNICI.KORISNIK WHERE ID = ?");
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            String korisnickoIme = rs.getString("KOR_IME");
            String lozinka = rs.getString("LOZINKA");
            Integer ovlastId = rs.getInt("OVLASTI");
            String ime = rs.getString("IME");
            String prezime = rs.getString("PREZIME");
            LocalDateTime aktivnost = rs.getTimestamp("AKTIVNOST")
                    .toLocalDateTime();

            Ovlasti ovlasti = null;
            for (Ovlasti temp : Ovlasti.values()) {
                if (temp.getId() == ovlastId) {
                    ovlasti = temp;
                }
            }

            korisnik = new Korisnik(id, korisnickoIme, lozinka, ime, prezime,
                    ovlasti, aktivnost);
        }

        ConnectionDatabase.odspajanjeSBaze(connection);
        return korisnik;
    }

    public synchronized static int dodajKorisnika() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement stmt = con
                .prepareStatement("INSERT INTO KORISNICI.KORISNIK (KOR_IME, LOZINKA, OVLASTI, AKTIVNOST, IME, PREZIME) VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setString(1, korisnik.getKorisnickoIme());
        stmt.setString(2, korisnik.getLozinka());
        stmt.setInt(3, korisnik.getOvlasti().getId());
        stmt.setTimestamp(4, Timestamp.valueOf(korisnik.getAktivnost()));
        stmt.setString(5, korisnik.getIme());
        stmt.setString(6, korisnik.getPrezime());
        stmt.executeUpdate();

        ConnectionDatabase.odspajanjeSBaze(con);
        
        return 1;
    }

    public synchronized static int obrisiKorisnika() throws SQLException {
        Connection con = ConnectionDatabase.spajanjeNaBazu();

        PreparedStatement stmt = con
                .prepareStatement("DELETE FROM KORISNICI.KORISNIK WHERE ID = ?");
        stmt.setInt(1, korisnik.getId());
        stmt.executeUpdate();

        ConnectionDatabase.odspajanjeSBaze(con);
        
        return 1;
    }

    public synchronized static int azurirajAktivnostKorisnika() throws SQLException {
        Connection connection = ConnectionDatabase.spajanjeNaBazu();
        PreparedStatement stmt = connection
                .prepareStatement("UPDATE KORISNICI.KORISNIK SET AKTIVNOST = ? WHERE ID = ?");

        stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
        stmt.setInt(2, Korisnik.trenutniKorisnik.getId());
        stmt.executeUpdate();

        ConnectionDatabase.odspajanjeSBaze(connection);
        
        return 1;
    }
    
    public synchronized static int azurirajKorisnika() throws SQLException {
            Connection connection = ConnectionDatabase.spajanjeNaBazu();
            PreparedStatement stmt = connection
                    .prepareStatement("UPDATE KORISNICI.KORISNIK SET KOR_IME = ?, LOZINKA = ?, OVLASTI = ?, AKTIVNOST = ?, IME = ?, PREZIME = ? WHERE ID = ?");
            
            stmt.setString(1, korisnik.getKorisnickoIme());
            stmt.setString(2, korisnik.getLozinka());
            stmt.setInt(3, korisnik.getOvlasti().getId());
            stmt.setTimestamp(4, Timestamp.valueOf(korisnik.getAktivnost()));
            stmt.setString(5, korisnik.getIme());
            stmt.setString(6, korisnik.getPrezime());
            stmt.setInt(7, korisnik.getId());
            stmt.executeUpdate();

            ConnectionDatabase.odspajanjeSBaze(connection);
            
            return 1;
        }


}
