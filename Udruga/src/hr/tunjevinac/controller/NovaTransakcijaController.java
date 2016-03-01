package hr.tunjevinac.controller;

import hr.tunjevinac.database.BlagajnaDatabase;
import hr.tunjevinac.dialog.ExceptionDialogs;
import hr.tunjevinac.entity.Korisnik;
import hr.tunjevinac.entity.Transakcija;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class NovaTransakcijaController extends ZajednickiController {

    BlagajnaDatabase callBlagajna = new BlagajnaDatabase();

    ObservableList<String> sat = FXCollections.observableArrayList();
    ObservableList<String> minuta = FXCollections.observableArrayList();
    @FXML
    private DatePicker datumDatePicker;
    @FXML
    private TextField iznosTextField;
    @FXML
    private RadioButton uplataRadioButton;
    @FXML
    private RadioButton isplataRadioButton;
    @FXML
    private TextField ibanTextField;
    @FXML
    private TextArea opisTextArea;
    @FXML
    private ComboBox<String> vrijemeH;
    @FXML
    private ComboBox<String> vrijemeM;
    private Transakcija transakcijaTemp;
    private boolean isEdit = false;

    @FXML
    public void initialize() {
        ToggleGroup group = new ToggleGroup();
        uplataRadioButton.setToggleGroup(group);
        isplataRadioButton.setToggleGroup(group);

        datumDatePicker.setPromptText("dd.mm.yyyy.");
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                sat.add("0" + i);
            } else
                sat.add(((Integer) i).toString());
        }
        vrijemeH.setItems(sat);
        vrijemeH.setPromptText("hh");

        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                minuta.add("0" + i);
            } else
                minuta.add(((Integer) i).toString());
        }
        vrijemeM.setItems(minuta);
        vrijemeM.setPromptText("mm");

        iznosTextField.setPromptText("iznos transakcije");
        ibanTextField.setPromptText("oznaka računa");
        opisTextArea.setPromptText("kratak opis u 100 znakova");
    }

    public NovaTransakcijaController() {
    }

    public void promjenaTransakcije(Transakcija transakcija) {
        isEdit = true;
        transakcijaTemp = transakcija;

        if (transakcijaTemp.getIznos().toString().contains("-")) {
            iznosTextField.setText(transakcijaTemp.getIznos().negate()
                    .toString());
            isplataRadioButton.setSelected(true);
        } else {
            iznosTextField.setText(transakcijaTemp.getIznos().toString());
            uplataRadioButton.setSelected(true);
        }

        ibanTextField.setText(transakcijaTemp.getIban());
        opisTextArea.setText(transakcijaTemp.getOpis());
        datumDatePicker.setValue(transakcijaTemp.getDatum().toLocalDate());
        vrijemeH.setValue(((Integer) transakcijaTemp.getDatum().toLocalTime()
                .getHour()).toString());
        vrijemeM.setValue(((Integer) transakcijaTemp.getDatum().toLocalTime()
                .getMinute()).toString());
    }

    @SuppressWarnings("unchecked")
    public void dodajTransakciju() {
        try {
            BlagajnaDatabase.selector = 1;
            List<Transakcija> lista = (List<Transakcija>) callBlagajna.call();

            BigDecimal iznos = null;

            if (uplataRadioButton.isSelected()) {
                iznos = new BigDecimal(iznosTextField.getText());
            }
            if (isplataRadioButton.isSelected()) {
                iznos = new BigDecimal("-" + iznosTextField.getText());
            }

            String iban = ibanTextField.getText();
            String opis = opisTextArea.getText();

            LocalDate datum = datumDatePicker.getValue();
            String vrijeme = vrijemeH.getValue() + ":" + vrijemeM.getValue()
                    + ":00";
            LocalTime time = LocalTime.parse(vrijeme,
                    DateTimeFormatter.ofPattern("HH:mm:ss"));
            LocalDateTime datumVrijeme = LocalDateTime.of(datum, time);

            List<Transakcija> pomLista = lista.stream()
                    .filter(p -> p.getDatum().isAfter(datumVrijeme))
                    .collect(Collectors.toList());

            List<Transakcija> pLista = lista.stream()
                    .filter(p -> p.getDatum().isBefore(datumVrijeme))
                    .collect(Collectors.toList());
            System.out.println(pLista.size()-1);

            BigDecimal saldo = pLista.get(pLista.size()-1).getSaldo();
            Transakcija transakcija = new Transakcija(0,
                    datumVrijeme, opis, iban, iznos, saldo.add(iznos),
                    Korisnik.trenutniKorisnik);

            transakcijaTemp = transakcija;
            if (!isEdit) {
                BlagajnaDatabase.selector = 2;
            } else {
                transakcija.setId(transakcijaTemp.getId());
                BlagajnaDatabase.selector = 4;
            }
            BlagajnaDatabase.transakcija = transakcijaTemp;
            
            idiNaBlagajnu();
            ZajednickiController.exeSer.submit(callBlagajna);

            for (Transakcija p : pomLista) {
                p.setSaldo(p.getSaldo().add(iznos));
                BlagajnaDatabase.selector = 4;
                BlagajnaDatabase.transakcija = p;
                ZajednickiController.exeSer.submit(callBlagajna);
            }


        } catch (SQLException e) {
            ExceptionDialogs.SQLDialog(e);
        } catch (IOException e) {
            ExceptionDialogs.IODialog(e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
