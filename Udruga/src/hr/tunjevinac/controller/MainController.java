package hr.tunjevinac.controller;

import hr.tunjevinac.dialog.ExceptionDialogs;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainController {

    @FXML
    private Hyperlink webStranica;
    @FXML
    private Hyperlink tunjevinac;
    @FXML
    private AnchorPane anchor;
    @FXML
    private BorderPane borderPane;

    @FXML
    private void initialize() {
    }

    private Desktop desktop;
    private URI uri;

    public void webStranica() {
        desktop = Desktop.getDesktop();
        try {
            uri = new URI("http://www.google.com");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            ExceptionDialogs.URIDialog(e);
        } catch (IOException e) {
            ExceptionDialogs.IODialog(e);
        }
    }

    public void tunjevinac() {
        desktop = Desktop.getDesktop();
        try {
            uri = new URI("https://www.linkedin.com/in/ajuratovic");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            ExceptionDialogs.URIDialog(e);
        } catch (IOException e) {
            ExceptionDialogs.IODialog(e);
        }
    }
}
