package hr.tunjevinac.controller;

import hr.tunjevinac.application.Main;
import hr.tunjevinac.database.KorisniciDatabase;
import hr.tunjevinac.dialog.ExceptionDialogs;
import hr.tunjevinac.dialog.InformationDialogs;
import hr.tunjevinac.entity.Korisnik;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class LoginController {

    @FXML
    private TextField korImeTextField;
    @FXML
    private PasswordField lozinkaPasswordField;
    @FXML
    private Button login;

    KorisniciDatabase callKorisnici = new KorisniciDatabase();

    @FXML
    public void initialize() {
        korImeTextField.setPromptText("korisničkoIme");
        lozinkaPasswordField.setPromptText("lozinka");
        if (login.isFocused()) {
            login.setDefaultButton(true);
            login.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent arg0) {
                    if (arg0.getCode().equals(KeyCode.ENTER)) {
                        login();
                    }
                }
            });
        }

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void login() {
        List<Korisnik> korisnici = new ArrayList<>();
        String korIme = korImeTextField.getText();
        try {
            String lozinka = Korisnik.computeHash(lozinkaPasswordField
                    .getText());
            boolean is = false;
            KorisniciDatabase.selector = 1;
            Future future = ZajednickiController.exeSer.submit(callKorisnici);
            korisnici = (List<Korisnik>) future.get();
            for (Korisnik temp : korisnici) {
                if (temp.getKorisnickoIme().equals(korIme)
                        && temp.getLozinka().equals(lozinka)) {
                    Korisnik.trenutniKorisnik = temp;
                    KorisniciDatabase.selector = 6;
                    KorisniciDatabase.id = temp.getId();
                    ZajednickiController.exeSer.submit(callKorisnici);

                    BorderPane pane = (BorderPane) FXMLLoader.load(Main.class
                            .getResource("../layout/Naslovna.fxml"));
                    Main.setCenterPane(pane);
                    is = true;
                }
            }
            if (!is) {
                InformationDialogs.loginDialog();
            }

        } catch (IOException e) {
            ExceptionDialogs.IODialog(e);
        } catch (NoSuchAlgorithmException e) {
            ExceptionDialogs.NoSuchAlgorithmDialog(e);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
