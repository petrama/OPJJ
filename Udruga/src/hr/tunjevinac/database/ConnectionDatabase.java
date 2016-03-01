package hr.tunjevinac.database;

import hr.tunjevinac.dialog.ExceptionDialogs;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDatabase {

    public static Connection spajanjeNaBazu() {
        Connection veza = null;
        try {
            Properties svojstva = new Properties();
            svojstva.load(new FileReader("database.properties"));

            String urlBazePodataka = svojstva.getProperty("databaseUrl");

            String korisnickoIme = svojstva.getProperty("username");
            String lozinka = svojstva.getProperty("password");

            veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme,
                    lozinka);
        } catch (SQLException ex) {
            ExceptionDialogs.SQLDialog(ex);
        } catch (IOException ex) {
            ExceptionDialogs.IODialog(ex);
        }
        return veza;

    }

    public static void odspajanjeSBaze(Connection veza) {
        try {
            veza.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
