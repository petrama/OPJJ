package hr.tunjevinac.dialog;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javafx.scene.control.Alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionDialogs {
    public static final Logger LOGGER = LoggerFactory
            .getLogger(ExceptionDialogs.class);

    public static void SQLDialog(SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Pogreška u radu");
        alert.setHeaderText("Pogreška u radu sa bazom podataka.");
        alert.setContentText(e.getMessage());
        LOGGER.error("SQLException", e);

        alert.showAndWait();
    }

    public static void NoSuchAlgorithmDialog(NoSuchAlgorithmException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Pogreška u radu");
        alert.setHeaderText("Pogreška kod enkripcije lozinke.");
        alert.setContentText(e.getMessage());
        LOGGER.error("NoSuchAlgorithmException", e);

        alert.showAndWait();
    }

    public static void IODialog(IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Pogreška u radu");
        alert.setHeaderText("Pogreška u radu sa datotekom.");
        alert.setContentText(e.getMessage());
        LOGGER.error("IOException", e);
System.out.println(e.getMessage());
        alert.showAndWait();
    }

    public static void URIDialog(URISyntaxException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Pogreška u radu");
        alert.setHeaderText("Pogreška kod otvaranja web stranice.");
        alert.setContentText(e.getMessage());
        LOGGER.error("SQLException", e);

        alert.showAndWait();
    }

    public static void InterruptedDialog(InterruptedException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Pogreška u radu");
        alert.setHeaderText("Pogreška u radu sustava višenitnosti.");
        alert.setContentText(e.getMessage());
        LOGGER.error("InterruptedException", e);

        alert.showAndWait();
    }

}
