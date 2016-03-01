package hr.tunjevinac.controller;

import hr.tunjevinac.database.KorisniciDatabase;
import hr.tunjevinac.dialog.ExceptionDialogs;
import hr.tunjevinac.entity.Korisnik;
import hr.tunjevinac.enumeration.Ovlasti;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class KorisniciController extends ZajednickiController {
    
    @FXML
    private DatePicker datumDatePicker;
    @FXML
    private TextField korImeTextField;
    @FXML
    private TableView<Korisnik> korisniciTable;
    @FXML
    private TableColumn<Korisnik, String> korImeColumn;
    @FXML
    private TableColumn<Korisnik, String> aktivnostColumn;
    @FXML
    private TableColumn<Korisnik, Ovlasti> ovlastiColumn;
    
    KorisniciDatabase callKorisnici = new KorisniciDatabase();

    @FXML
    public void initialize() {
        korisniciTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() %2 == 0 && !event.isConsumed()) {
                    
                    event.consume();
                    
                    try {
                        noviKorisnik();
                        Korisnik korisnik = korisniciTable.getSelectionModel()
                                .getSelectedItem();
                        NoviKorisnikController temp = ZajednickiController.loader
                                .<NoviKorisnikController> getController();
                        temp.promjenaKorisnika(korisnik);
                    } catch (IOException e) {
                        ExceptionDialogs.IODialog(e);
                    }
                    }
                }
            }

        });
        korImeColumn
                .setCellValueFactory(new PropertyValueFactory<Korisnik, String>(
                        "korisnickoIme"));
        aktivnostColumn
                .setCellValueFactory(new PropertyValueFactory<Korisnik, String>(
                        "aktivnostS"));
        ovlastiColumn
                .setCellValueFactory(new PropertyValueFactory<Korisnik, Ovlasti>(
                        "ovlasti"));
        datumDatePicker.setPromptText("dd.mm.yyyy.");
        korImeTextField.setPromptText("korisničkoIme");
        dohvatiKorisnike();
        
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void dohvatiKorisnike() {
        List<Korisnik> korisnici = new ArrayList<>();
        LocalTime time = LocalTime.of(0, 0);
        try {
            KorisniciDatabase.selector = 1;
            Future future = ZajednickiController.exeSer.submit(callKorisnici);
            korisnici = (List<Korisnik>) future.get();
            if (datumDatePicker == null) {
                LocalDateTime datum = LocalDateTime.of(
                        datumDatePicker.getValue(), time);
                korisnici = korisnici.stream()
                        .filter(p -> p.getAktivnost().isBefore(datum))
                        .collect(Collectors.toList());
            }

            if (!korImeTextField.getText().isEmpty()) {
                korisnici = korisnici
                        .stream()
                        .filter(p -> p.getKorisnickoIme().contains(
                                korImeTextField.getText()))
                        .collect(Collectors.toList());
            }

            ObservableList<Korisnik> korisniciObservable = FXCollections
                    .observableArrayList(korisnici);
            korisniciTable.setItems(korisniciObservable);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void obrisiKorisnika() {
        Korisnik korisnik = korisniciTable.getSelectionModel()
                .getSelectedItem();

        try {
            KorisniciDatabase.selector = 3;
            KorisniciDatabase.korisnik = korisnik;
            ZajednickiController.exeSer.submit(callKorisnici);
            dohvatiKorisnike();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
