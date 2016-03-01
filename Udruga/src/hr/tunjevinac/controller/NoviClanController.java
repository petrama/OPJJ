package hr.tunjevinac.controller;

import hr.tunjevinac.database.ClanoviDatabase;
import hr.tunjevinac.entity.Clan;
import hr.tunjevinac.enumeration.Status;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class NoviClanController extends ZajednickiController {
    ClanoviDatabase callClanovi = new ClanoviDatabase();
    ObservableList<Status> statusi = FXCollections.observableArrayList();
    @FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private TextField kontBrojTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField oibTextField;
    @FXML
    private ComboBox<Status> statusComboBox;
    private boolean isEdit = false;
    private Clan clanTemp;

    @FXML
    public void intialize() {

        statusi.addAll(Status.values());
        statusComboBox.setItems(statusi);
        statusComboBox.setPromptText("pozicija unutar udruge");

        imeTextField.setPromptText("ime novog člana");
        prezimeTextField.setPromptText("prezime novog člana");
        oibTextField.setPromptText("OIB novog člana");
        kontBrojTextField.setPromptText("kontakt broj novog člana, neobavezno");
        emailTextField.setPromptText("e-mail novog člana, neobavezno");
    }

    public NoviClanController() {
    }

    public void promjenaClana(Clan clan) {
        isEdit = true;
        clanTemp = clan;

        imeTextField.setText(clanTemp.getIme());
        prezimeTextField.setText(clanTemp.getPrezime());
        kontBrojTextField.setText(clanTemp.getKontaktBroj());
        emailTextField.setText(clanTemp.getEmail());
        oibTextField.setText(clanTemp.getOib());
        statusComboBox.setValue(clanTemp.getStatus());
    }

    @SuppressWarnings("unchecked")
    public void dodajClana() throws IOException {
        try {

            String ime = imeTextField.getText();
            String prezime = prezimeTextField.getText();
            String kontBroj = kontBrojTextField.getText();
            String email = emailTextField.getText();
            String oib = oibTextField.getText();
            Status status = statusComboBox.getValue();

            Clan clan = new Clan(clanTemp.getId(), ime, prezime, oib, kontBroj,
                    email, status);
            clanTemp = clan;
            if (!isEdit) {
                ClanoviDatabase.selector = 2;
            } else {
                ClanoviDatabase.selector = 4;
            }
            ClanoviDatabase.clan = clanTemp;
            
            idiNaClanstvo();
            ZajednickiController.exeSer.submit(callClanovi);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
