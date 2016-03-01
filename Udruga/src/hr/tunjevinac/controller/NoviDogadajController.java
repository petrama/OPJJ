package hr.tunjevinac.controller;

import hr.tunjevinac.database.KalendarDatabase;
import hr.tunjevinac.entity.Dogadaj;
import hr.tunjevinac.entity.Korisnik;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NoviDogadajController extends ZajednickiController {

    KalendarDatabase callKalendar = new KalendarDatabase();
    ObservableList<String> sat = FXCollections.observableArrayList();
    ObservableList<String> minuta = FXCollections.observableArrayList();
    @FXML
    private TextField naslovTextField;
    @FXML
    private TextField mjestoTextField;
    @FXML
    private TextArea opisTextArea;
    @FXML
    private DatePicker datumDatePicker;
    @FXML
    private ComboBox<String> satComboBox;
    @FXML
    private ComboBox<String> minComboBox;
    private Dogadaj dogadajTemp;
    private boolean isEdit = false;

    @FXML
    public void intialize() {
        datumDatePicker.setPromptText("dd.mm.yyyy.");
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                sat.add("0" + i);
            } else
                sat.add(((Integer) i).toString());
        }
        satComboBox.setItems(sat);
        satComboBox.setPromptText("hh");

        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                minuta.add("0" + i);
            } else
                minuta.add(((Integer) i).toString());
        }
        minComboBox.setItems(minuta);
        minComboBox.setPromptText("mm");

        naslovTextField.setPromptText("naziv zabilježenog događaja");
        mjestoTextField.setPromptText("lokacija događaja");
        opisTextArea.setPromptText("kratak opis aktivnosti");
    }

    public NoviDogadajController() {
    }

    public void promjenaDogadaja(Dogadaj dogadaj) {
        isEdit = true;
        dogadajTemp = dogadaj;

        naslovTextField.setText(dogadajTemp.getNaslov());
        mjestoTextField.setText(dogadajTemp.getMjesto());
        opisTextArea.setText(dogadajTemp.getOpis());
        datumDatePicker.setValue(dogadajTemp.getDatum().toLocalDate());
        satComboBox.setValue(((Integer) dogadajTemp.getDatum().toLocalTime()
                .getHour()).toString());
        minComboBox.setValue(((Integer) dogadajTemp.getDatum().toLocalTime()
                .getMinute()).toString());
    }

    @SuppressWarnings("unchecked")
    public void dodajDogadaj() throws IOException {
        try {

            LocalDate datum = datumDatePicker.getValue();
            String vrijeme = satComboBox.getValue() + ":"
                    + minComboBox.getValue() + ":00";
            LocalTime time = LocalTime.parse(vrijeme,
                    DateTimeFormatter.ofPattern("HH:mm:ss"));
            LocalDateTime datumVrijeme = LocalDateTime.of(datum, time);

            String naslov = naslovTextField.getText();
            String mjesto = mjestoTextField.getText();
            String opis = opisTextArea.getText();

            Dogadaj dogadaj = new Dogadaj(dogadajTemp.getId(), datumVrijeme,
                    naslov, opis, mjesto, Korisnik.trenutniKorisnik);
            dogadajTemp = dogadaj;

            if (!isEdit) {
                KalendarDatabase.selector = 2;
            } else {
                KalendarDatabase.selector = 4;
            }

            KalendarDatabase.dogadaj = dogadaj;

            idiNaKalendar();
            ZajednickiController.exeSer.submit(callKalendar);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
