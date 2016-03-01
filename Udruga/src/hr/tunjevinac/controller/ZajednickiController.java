package hr.tunjevinac.controller;

import hr.tunjevinac.application.Main;
import hr.tunjevinac.entity.Korisnik;
import hr.tunjevinac.enumeration.Ovlasti;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class ZajednickiController {

    public static ExecutorService exeSer = Executors.newCachedThreadPool();
    public static FXMLLoader loader;
        
    public void idiNaNaslovnu() throws IOException {
        BorderPane pane = (BorderPane) FXMLLoader.load(Main.class
                .getResource("../layout/Naslovna.fxml"));
        Main.setCenterPane(pane);
    }

    public void idiNaBlagajnu() throws IOException {
        BorderPane pane = (BorderPane) FXMLLoader.load(Main.class
                .getResource("../layout/Blagajna.fxml"));
        Main.setCenterPane(pane);
    }

    public void novaTransakcija() throws IOException {
        loader = new FXMLLoader(Main.class
                .getResource("../layout/NovaTransakcija.fxml"));
        BorderPane pane = (BorderPane) loader.load();
        Main.setCenterPane(pane);
    }

    public void idiNaClanstvo() throws IOException {
        BorderPane pane = (BorderPane) FXMLLoader.load(Main.class
                .getResource("../layout/Clanstvo.fxml"));
        Main.setCenterPane(pane);
    }

    public void noviClan() throws IOException {
        loader = new FXMLLoader(Main.class
                .getResource("../layout/NoviClan.fxml"));
        BorderPane pane = (BorderPane) loader.load();
        Main.setCenterPane(pane);
    }

    public void idiNaKalendar() throws IOException {
        BorderPane pane = (BorderPane) FXMLLoader.load(Main.class
                .getResource("../layout/Kalendar.fxml"));
        Main.setCenterPane(pane);
    }

    public void noviDogadaj() throws IOException {
        loader = new FXMLLoader(Main.class
                .getResource("../layout/NoviDogadaj.fxml"));
        BorderPane pane = (BorderPane) loader.load();
        Main.setCenterPane(pane);
    }

    public void idiNaKorisnike() throws IOException {
        if (Korisnik.trenutniKorisnik.getOvlasti()
                .equals(Ovlasti.ADMINISTRATOR)) {
            BorderPane pane = (BorderPane) FXMLLoader.load(Main.class
                    .getResource("../layout/Korisnici.fxml"));
            Main.setCenterPane(pane);
        } else {
            noviKorisnik();
            NoviKorisnikController temp = ZajednickiController.loader
                    .<NoviKorisnikController> getController();
            temp.promjenaKorisnika(Korisnik.trenutniKorisnik);
        }
    }

    public void noviKorisnik() throws IOException {
        loader = new FXMLLoader(Main.class
                .getResource("../layout/NoviKorisnik.fxml"));
        BorderPane pane = (BorderPane) loader.load();
        Main.setCenterPane(pane);
    }

    public void idiNaZapisnik() throws IOException {
        BorderPane pane = (BorderPane) FXMLLoader.load(Main.class
                .getResource("../layout/Zapisnik.fxml"));
        Main.setCenterPane(pane);
    }

    public void noviZapisnik() throws IOException {
        loader = new FXMLLoader(Main.class
                .getResource("../layout/NoviZapisnik.fxml"));
        BorderPane pane = (BorderPane) loader.load();
        Main.setCenterPane(pane);
    }
}
