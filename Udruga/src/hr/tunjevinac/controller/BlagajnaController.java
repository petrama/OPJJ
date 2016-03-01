package hr.tunjevinac.controller;

import hr.tunjevinac.database.BlagajnaDatabase;
import hr.tunjevinac.dialog.ExceptionDialogs;
import hr.tunjevinac.entity.Transakcija;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class BlagajnaController extends ZajednickiController {

    BlagajnaDatabase callBlagajna = new BlagajnaDatabase();

    @FXML
    private TableView<Transakcija> blagajnaTable;
    @FXML
    private TableColumn<Transakcija, String> datumColumn;
    @FXML
    private TableColumn<Transakcija, String> opisColumn;
    @FXML
    private TableColumn<Transakcija, String> ibanColumn;
    @FXML
    private TableColumn<Transakcija, BigDecimal> iznosColumn;
    @FXML
    private TableColumn<Transakcija, BigDecimal> saldoColumn;
    @FXML
    private TableColumn<Transakcija, String> autorColumn;
    @FXML
    private DatePicker odDatePicker;
    @FXML
    private DatePicker doDatePicker;
    @FXML
    private CheckBox uplataCheckBox;
    @FXML
    private CheckBox isplataCheckBox;

    @FXML
    public void initialize() {
        blagajnaTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() % 2 == 0 && !event.isConsumed()) {

                        event.consume();

                        try {
                            novaTransakcija();
                            Transakcija transakcija = blagajnaTable
                                    .getSelectionModel().getSelectedItem();
                            NovaTransakcijaController temp = ZajednickiController.loader
                                    .<NovaTransakcijaController> getController();
                            temp.promjenaTransakcije(transakcija);
                        } catch (IOException e) {
                            ExceptionDialogs.IODialog(e);
                        }
                    }
                }
            }

        });
        datumColumn
                .setCellValueFactory(new PropertyValueFactory<Transakcija, String>(
                        "datumS"));
        opisColumn
                .setCellValueFactory(new PropertyValueFactory<Transakcija, String>(
                        "opis"));
        ibanColumn
                .setCellValueFactory(new PropertyValueFactory<Transakcija, String>(
                        "iban"));
        iznosColumn
                .setCellValueFactory(new PropertyValueFactory<Transakcija, BigDecimal>(
                        "iznos"));
        saldoColumn
                .setCellValueFactory(new PropertyValueFactory<Transakcija, BigDecimal>(
                        "saldo"));
        autorColumn
                .setCellValueFactory(new PropertyValueFactory<Transakcija, String>(
                        "autorS"));
        odDatePicker.setPromptText("dd.mm.yyyy.");
        doDatePicker.setPromptText("dd.mm.yyyy.");
        dohvatiTransakcije();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void dohvatiTransakcije() {
        List<Transakcija> transakcije = new ArrayList<>();
        LocalTime time = LocalTime.parse("00:00",
                DateTimeFormatter.ofPattern("HH:mm"));

        try {
            BlagajnaDatabase.selector = 1;
            Future future = ZajednickiController.exeSer.submit(callBlagajna);
            transakcije = (List<Transakcija>) future.get();
            if (odDatePicker == null) {
                transakcije = transakcije
                        .stream()
                        .filter(p -> p.getDatum()
                                .isAfter(
                                        LocalDateTime.of(
                                                odDatePicker.getValue(), time)))
                        .collect(Collectors.toList());
            }
            if (doDatePicker == null) {
                transakcije = transakcije
                        .stream()
                        .filter(p -> p.getDatum()
                                .isBefore(
                                        LocalDateTime.of(
                                                doDatePicker.getValue(), time)))
                        .collect(Collectors.toList());
            }

            if (isplataCheckBox.isSelected()) {
                transakcije = transakcije
                        .stream()
                        .filter(p -> (p.getIznos().compareTo(BigDecimal.ZERO) < 0))
                        .collect(Collectors.toList());
            }
            if (uplataCheckBox.isSelected()) {
                transakcije = transakcije
                        .stream()
                        .filter(p -> (p.getIznos().compareTo(BigDecimal.ZERO) > 0))
                        .collect(Collectors.toList());
            }
            ObservableList<Transakcija> transakcijeObservable = FXCollections
                    .observableArrayList(transakcije);
            blagajnaTable.setItems(transakcijeObservable);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void obrisiTransakciju() {

        try {
            Transakcija transakcija = blagajnaTable.getSelectionModel()
                    .getSelectedItem();

            List<Transakcija> lista = blagajnaTable.getItems().stream()
                    .filter(p -> p.getDatum().isAfter(transakcija.getDatum()))
                    .collect(Collectors.toList());

            for (Transakcija p : lista) {
                p.setSaldo(p.getSaldo().add(transakcija.getIznos().negate()));
                BlagajnaDatabase.selector = 4;
                BlagajnaDatabase.transakcija = p;
                callBlagajna.call();
            }

            BlagajnaDatabase.selector = 3;
            BlagajnaDatabase.transakcija = transakcija;
            ZajednickiController.exeSer.submit(callBlagajna);

            dohvatiTransakcije();
        } catch (SQLException e) {
            ExceptionDialogs.SQLDialog(e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
