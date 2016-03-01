package hr.tunjevinac.controller;

import hr.tunjevinac.database.KalendarDatabase;
import hr.tunjevinac.dialog.ExceptionDialogs;
import hr.tunjevinac.entity.Dogadaj;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

public class KalendarController extends ZajednickiController {

    @FXML
    private TextField naslovTextField;
    @FXML
    private DatePicker odDatePicker;
    @FXML
    private DatePicker doDatePicker;
    @FXML
    private TableView<Dogadaj> kalendarTable;
    @FXML
    private TableColumn<Dogadaj, String> datumColumn;
    @FXML
    private TableColumn<Dogadaj, String> naslovColumn;
    @FXML
    private TableColumn<Dogadaj, String> mjestoColumn;
    @FXML
    private TableColumn<Dogadaj, String> opisColumn;
    @FXML
    private TableColumn<Dogadaj, String> autorColumn;

    KalendarDatabase callKalendar = new KalendarDatabase();
    
    @FXML
    public void initialize() {
        kalendarTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() % 2 == 0 && !event.isConsumed()) {

                        event.consume();

                        try {
                            novaTransakcija();
                            Dogadaj dogadaj = kalendarTable
                                    .getSelectionModel().getSelectedItem();
                            NoviDogadajController temp = ZajednickiController.loader
                                    .<NoviDogadajController> getController();
                            temp.promjenaDogadaja(dogadaj);
                        } catch (IOException e) {
                            ExceptionDialogs.IODialog(e);
                        }
                    }
                }
            }

        });
        datumColumn
                .setCellValueFactory(new PropertyValueFactory<Dogadaj, String>(
                        "datumS"));
        naslovColumn
                .setCellValueFactory(new PropertyValueFactory<Dogadaj, String>(
                        "naslov"));
        mjestoColumn
                .setCellValueFactory(new PropertyValueFactory<Dogadaj, String>(
                        "mjesto"));
        opisColumn
                .setCellValueFactory(new PropertyValueFactory<Dogadaj, String>(
                        "opis"));
        autorColumn
                .setCellValueFactory(new PropertyValueFactory<Dogadaj, String>(
                        "autorS"));
        naslovTextField.setPromptText("unesite naziv događaja");
        odDatePicker.setValue(LocalDate.now());
        odDatePicker.setPromptText("dd.mm.yyyy.");
        doDatePicker.setPromptText("dd.mm.yyyy.");
        dohvatiDogadaje();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void dohvatiDogadaje() {
        List<Dogadaj> dogadaji = new ArrayList<>();
        LocalTime time = LocalTime.parse("00:00",
                DateTimeFormatter.ofPattern("HH:mm"));

        try {
            KalendarDatabase.selector = 1;
            Future future = ZajednickiController.exeSer.submit(callKalendar);
            dogadaji = (List<Dogadaj>) future.get();
            if (odDatePicker == null) {
                dogadaji = dogadaji
                        .stream()
                        .filter(p -> p.getDatum()
                                .isAfter(
                                        LocalDateTime.of(
                                                odDatePicker.getValue(), time)))
                        .collect(Collectors.toList());
            }
            if (doDatePicker == null) {
                dogadaji = dogadaji
                        .stream()
                        .filter(p -> p.getDatum()
                                .isBefore(
                                        LocalDateTime.of(
                                                doDatePicker.getValue(), time)))
                        .collect(Collectors.toList());
            }

            if (!naslovTextField.getText().isEmpty()) {
                dogadaji = dogadaji
                        .stream()
                        .filter(p -> p.getNaslov().contains(
                                naslovTextField.getText()))
                        .collect(Collectors.toList());
            }

            ObservableList<Dogadaj> dogadajiObservable = FXCollections
                    .observableArrayList(dogadaji);
            if (!dogadajiObservable.isEmpty()) {
                kalendarTable.setItems(dogadajiObservable);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void obrisiDogadaj() {

        try {
            Dogadaj dogadaj = kalendarTable.getSelectionModel()
                    .getSelectedItem();

            KalendarDatabase.selector = 3;
            KalendarDatabase.dogadaj = dogadaj;
            
            ZajednickiController.exeSer.submit(callKalendar);
            
            dohvatiDogadaje();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
