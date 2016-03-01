package hr.tunjevinac.controller;

import hr.tunjevinac.database.ClanoviDatabase;
import hr.tunjevinac.dialog.ExceptionDialogs;
import hr.tunjevinac.entity.Clan;
import hr.tunjevinac.enumeration.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ClanstvoController extends ZajednickiController {

    @FXML
    private TableView<Clan> clanTable;
    @FXML
    private TableColumn<Clan, String> imeColumn;
    @FXML
    private TableColumn<Clan, String> prezimeColumn;
    @FXML
    private TableColumn<Clan, String> oibColumn;
    @FXML
    private TableColumn<Clan, String> kontBrojColumn;
    @FXML
    private TableColumn<Clan, String> emailColumn;
    @FXML
    private TableColumn<Clan, Status> statusColumn;
    @FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private TextField oibTextField;
    @FXML
    private CheckBox clanCheck;
    @FXML
    private CheckBox funkcijeCheck;
    @FXML
    private CheckBox kontBrojCheck;
    @FXML
    private CheckBox emailCheck;
    
    ClanoviDatabase callClanovi = new ClanoviDatabase();
    
    @FXML
    public void initialize() {
        clanTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() % 2 == 0 && !event.isConsumed()) {

                        event.consume();

                        try {
                            noviClan();
                            Clan clan = clanTable
                                    .getSelectionModel().getSelectedItem();
                            NoviClanController temp = ZajednickiController.loader
                                    .<NoviClanController> getController();
                            temp.promjenaClana(clan);
                        } catch (IOException e) {
                            ExceptionDialogs.IODialog(e);
                        }
                    }
                }
            }

        });
        imeTextField.setPromptText("unesite ime traženog člana");
        prezimeTextField.setPromptText("unesite prezime traženog člana");
        oibTextField.setPromptText("unesite OIB traženog člana");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void dohvatiClanove() {
        List<Clan> clanovi = new ArrayList<>();

        try {
            ClanoviDatabase.selector = 1;
            Future future = ZajednickiController.exeSer.submit(callClanovi);
            clanovi = (List<Clan>) future.get();

            if (clanCheck.isSelected()) {
                clanovi = clanovi.stream()
                        .filter(p -> p.getStatus().equals(Status.CLAN))
                        .collect(Collectors.toList());
            }

            if (funkcijeCheck.isSelected()) {
                clanovi = clanovi.stream()
                        .filter(p -> !p.getStatus().equals(Status.CLAN))
                        .collect(Collectors.toList());
            }

            if (kontBrojCheck.isSelected()) {
                clanovi = clanovi.stream()
                        .filter(p -> !p.getKontaktBroj().isEmpty())
                        .collect(Collectors.toList());
            }

            if (emailCheck.isSelected()) {
                clanovi = clanovi.stream()
                        .filter(p -> !p.getKontaktBroj().isEmpty())
                        .collect(Collectors.toList());
            }

            if (!imeTextField.getText().isEmpty()) {
                clanovi = clanovi
                        .stream()
                        .filter(p -> p.getIme()
                                .contains(imeTextField.getText()))
                        .collect(Collectors.toList());
            }

            if (!prezimeTextField.getText().isEmpty()) {
                clanovi = clanovi
                        .stream()
                        .filter(p -> p.getPrezime().contains(
                                prezimeTextField.getText()))
                        .collect(Collectors.toList());
            }

            if (!oibTextField.getText().isEmpty()) {
                clanovi = clanovi
                        .stream()
                        .filter(p -> p.getOib()
                                .contains(oibTextField.getText()))
                        .collect(Collectors.toList());
            }

            ObservableList<Clan> clanoviObservable = FXCollections
                    .observableArrayList(clanovi);
            if (!clanoviObservable.isEmpty()) {
                clanTable.setItems(clanoviObservable);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void obrisiClana() {

        try {
            Clan clan = clanTable.getSelectionModel().getSelectedItem();
            
            ClanoviDatabase.selector = 3;
            ClanoviDatabase.clan = clan;
            ZajednickiController.exeSer.submit(callClanovi);
            dohvatiClanove();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
