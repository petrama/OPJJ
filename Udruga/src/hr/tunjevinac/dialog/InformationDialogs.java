package hr.tunjevinac.dialog;

import hr.tunjevinac.entity.Korisnik;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InformationDialogs {

    private static Alert alert = new Alert(AlertType.NONE);
    public static Logger LOGGER = LoggerFactory
            .getLogger(InformationDialogs.class);

    public static void administratorDialog() {
        alert.setAlertType(AlertType.WARNING);
        alert.setTitle("Upozorenje!");
        alert.setHeaderText(null);
        alert.setContentText("Ovlasti Vašeg korisničkog računa nisu dovoljne za upravljanje korisnicima.");
        LOGGER.error(Korisnik.trenutniKorisnik.getKorisnickoIme()
                + " pokušao upravljati korisnicima.");

        alert.showAndWait();
    }

    public static void loginDialog() {
        alert.setAlertType(AlertType.WARNING);
        alert.setTitle("Upozorenje!");
        alert.setHeaderText(null);
        alert.setContentText("Korisnički podaci nisu ispravni");

        alert.showAndWait();
    }
}
