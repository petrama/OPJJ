package hr.tunjevinac.controller;

import hr.tunjevinac.database.KorisniciDatabase;
import hr.tunjevinac.dialog.ExceptionDialogs;
import hr.tunjevinac.entity.Korisnik;
import hr.tunjevinac.enumeration.Ovlasti;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class NoviKorisnikController extends ZajednickiController {

    @FXML
    private ComboBox<Ovlasti> ovlastiComboBox;
    @FXML
    private TextField korImeTextField;
    @FXML
    private PasswordField lozinkaPasswordField;
    @FXML
    private PasswordField ponovljenaLozinkaPasswordField;
    @FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    private Korisnik korisnikTemp;
    private boolean isEdit = false;

    KorisniciDatabase callKorisnici = new KorisniciDatabase();

    @FXML
    public void initialize() {
        ObservableList<Ovlasti> ovlasti = FXCollections
                .observableArrayList(Ovlasti.values());
        ovlastiComboBox.getItems().addAll(ovlasti);
        ovlastiComboBox.setPromptText("odaberite...");

        korImeTextField.setPromptText("bez dijakritičkih znakova");
        lozinkaPasswordField.setPromptText("bez dijakritičkih znakova");
        ponovljenaLozinkaPasswordField.setPromptText("ponovite upis lozinke");
        imeTextField.setPromptText("upišite vaše ime");
        prezimeTextField.setPromptText("upišite vaše prezime");
    }

    public NoviKorisnikController() {
    }

    public void promjenaKorisnika(Korisnik korisnik) {
        isEdit = true;
        korisnikTemp = korisnik;

        korImeTextField.setText(korisnikTemp.getKorisnickoIme());
        lozinkaPasswordField.setText(korisnikTemp.getLozinka());
        ponovljenaLozinkaPasswordField.setText(korisnikTemp.getLozinka());
        imeTextField.setText(korisnikTemp.getIme());
        prezimeTextField.setText(korisnikTemp.getPrezime());
        ovlastiComboBox.setValue(korisnikTemp.getOvlasti());
    }

    @SuppressWarnings("unchecked")
    public void dodajKorisnika() {
        try {
            
            String korIme = korImeTextField.getText();
            String lozinka = lozinkaPasswordField.getText();
            String lozinkaPonovljena = ponovljenaLozinkaPasswordField.getText();

            String lozinkaEnc = Korisnik.computeHash(lozinka);
            String lozinkaPonovljenaEnc = Korisnik
                    .computeHash(lozinkaPonovljena);
            String ime = imeTextField.getText();
            String prezime = prezimeTextField.getText();

            if (lozinkaEnc.equals(lozinkaPonovljenaEnc)) {
                Korisnik korisnikNovi = new Korisnik(korisnikTemp.getId(),
                        korIme, lozinkaEnc, ime, prezime,
                        ovlastiComboBox.getValue(), LocalDateTime.now());
                korisnikTemp = korisnikNovi;
                if (!isEdit) {
                    KorisniciDatabase.selector = 2;
                } else
                    KorisniciDatabase.selector = 4;

                KorisniciDatabase.korisnik = korisnikTemp;                

                if (Korisnik.trenutniKorisnik.getOvlasti().equals(
                        Ovlasti.ADMINISTRATOR)) {
                    idiNaKorisnike();
                } else {
                    idiNaNaslovnu();
                }
                ZajednickiController.exeSer.submit(callKorisnici);

                
            }

        } catch (IOException e) {
            ExceptionDialogs.IODialog(e);
        } catch (NoSuchAlgorithmException e) {
            ExceptionDialogs.NoSuchAlgorithmDialog(e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
