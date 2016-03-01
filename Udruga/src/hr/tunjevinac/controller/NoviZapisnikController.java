package hr.tunjevinac.controller;

import hr.tunjevinac.database.ZapisnikDatabase;
import hr.tunjevinac.dialog.ExceptionDialogs;
import hr.tunjevinac.entity.Korisnik;
import hr.tunjevinac.entity.Zapis;

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
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

public class NoviZapisnikController extends ZajednickiController {

    ZapisnikDatabase callZapisnik = new ZapisnikDatabase();

    ObservableList<String> sat = FXCollections.observableArrayList();
    ObservableList<String> minuta = FXCollections.observableArrayList();
    @FXML
    private DatePicker datumDatePicker;
    @FXML
    private TextField naslovTextField;
    @FXML
    private ComboBox<String> satComboBox;
    @FXML
    private ComboBox<String> minuteComboBox;
    @FXML
    private HTMLEditor sadrzajHTMLEditor;
    private Zapis zapisTemp;
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
        minuteComboBox.setItems(minuta);
        minuteComboBox.setPromptText("mm");

        naslovTextField.setPromptText("naziv zabilježenog događaja");
    }

    public NoviZapisnikController() {
    }

    public void promjenaZapisa(Zapis zapis) {
        isEdit = true;
        zapisTemp = zapis;

        naslovTextField.setText(zapisTemp.getNaslov());
        sadrzajHTMLEditor.setHtmlText(zapisTemp.getZapis());
        datumDatePicker.setValue(zapisTemp.getDatum().toLocalDate());
        satComboBox.setValue(((Integer) zapisTemp.getDatum().toLocalTime()
                .getHour()).toString());
        minuteComboBox.setValue(((Integer) zapisTemp.getDatum().toLocalTime()
                .getMinute()).toString());
    }

    @SuppressWarnings("unchecked")
    public void dodajStavku() {
        try {
            LocalDate datum = datumDatePicker.getValue();
            String vrijeme = satComboBox.getValue() + ":"
                    + minuteComboBox.getValue() + ":00";
            LocalTime time = LocalTime.parse(vrijeme,
                    DateTimeFormatter.ofPattern("HH:mm:ss"));
            LocalDateTime datumVrijeme = LocalDateTime.of(datum, time);

            String naslov = naslovTextField.getText();
            String sadrzaj = sadrzajHTMLEditor.getHtmlText();

            Zapis zapis = new Zapis(zapisTemp.getId(), datumVrijeme, naslov,
                    sadrzaj, Korisnik.trenutniKorisnik);
            zapisTemp = zapis;

            if (!isEdit) {
                ZapisnikDatabase.selector = 2;
            } else {
                ZapisnikDatabase.selector = 4;
            }
            ZapisnikDatabase.zapis = zapisTemp;

            idiNaZapisnik();

            ZajednickiController.exeSer.submit(callZapisnik);
        } catch (IOException e) {
            ExceptionDialogs.IODialog(e);
        }
    }
}
