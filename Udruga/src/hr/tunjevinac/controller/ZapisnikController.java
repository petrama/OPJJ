package hr.tunjevinac.controller;

import hr.tunjevinac.database.ZapisnikDatabase;
import hr.tunjevinac.dialog.ExceptionDialogs;
import hr.tunjevinac.entity.Zapis;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
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

public class ZapisnikController extends ZajednickiController {

    ZapisnikDatabase callZapisnik = new ZapisnikDatabase();
    
    @FXML
    private DatePicker odDatePicker;
    @FXML
    private DatePicker doDatePicker;
    @FXML
    private TextField sadrzajTextField;
    @FXML
    private TableView<Zapis> zapisTable;
    @FXML
    private TableColumn<Zapis, String> datumColumn;
    @FXML
    private TableColumn<Zapis, String> naslovColumn;
    @FXML
    private TableColumn<Zapis, String> sadrzajColumn;
    @FXML
    private TableColumn<Zapis, String> autorColumn;

    @FXML
    public void initialize() {
        zapisTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() % 2 == 0 && !event.isConsumed()) {

                        event.consume();

                        try {
                            novaTransakcija();
                            Zapis zapis = zapisTable
                                    .getSelectionModel().getSelectedItem();
                            NoviZapisnikController temp = ZajednickiController.loader
                                    .<NoviZapisnikController> getController();
                            temp.promjenaZapisa(zapis);
                        } catch (IOException e) {
                            ExceptionDialogs.IODialog(e);
                        }
                    }
                }
            }

        });
        datumColumn
                .setCellValueFactory(new PropertyValueFactory<Zapis, String>(
                        "datumS"));
        naslovColumn
                .setCellValueFactory(new PropertyValueFactory<Zapis, String>(
                        "naslov"));
        sadrzajColumn
                .setCellValueFactory(new PropertyValueFactory<Zapis, String>(
                        "sadrzaj"));
        autorColumn
                .setCellValueFactory(new PropertyValueFactory<Zapis, String>(
                        "autorS"));
        odDatePicker.setPromptText("dd.mm.yyyy.");
        doDatePicker.setPromptText("dd.mm.yyyy.");
        sadrzajTextField.setPromptText("upišite dio traženog zapisa");
        dohvatiZapise();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void dohvatiZapise() {
        List<Zapis> zapisi = new ArrayList<>();
        LocalTime time = LocalTime.parse("00:00",
                DateTimeFormatter.ofPattern("HH:mm"));

        try {
            ZapisnikDatabase.selector = 1;
            Future future = ZajednickiController.exeSer.submit(callZapisnik);
            zapisi = (List<Zapis>) future.get();
            if (odDatePicker == null) {
                zapisi = zapisi
                        .stream()
                        .filter(p -> p.getDatum()
                                .isAfter(
                                        LocalDateTime.of(
                                                odDatePicker.getValue(), time)))
                        .collect(Collectors.toList());
            }
            if (doDatePicker == null) {
                zapisi = zapisi
                        .stream()
                        .filter(p -> p.getDatum()
                                .isBefore(
                                        LocalDateTime.of(
                                                doDatePicker.getValue(), time)))
                        .collect(Collectors.toList());
            }

            if (!sadrzajTextField.getText().isEmpty()) {
                zapisi = zapisi
                        .stream()
                        .filter(p -> p.getZapis().contains(
                                sadrzajTextField.getText()))
                        .collect(Collectors.toList());
            }

            ObservableList<Zapis> zapisiObservable = FXCollections
                    .observableArrayList(zapisi);
            if (!zapisiObservable.isEmpty()) {
                zapisTable.setItems(zapisiObservable);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void obrisiZapis() {

        Zapis zapis = zapisTable.getSelectionModel().getSelectedItem();
        
        ZapisnikDatabase.selector = 3;
        ZapisnikDatabase.zapis = zapis;
        ZajednickiController.exeSer.submit(callZapisnik);
        dohvatiZapise();
    }
}
