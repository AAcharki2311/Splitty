package client.scenes;

import client.utils.languageSwitchInterface;
import commons.Participant;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditParticipantCtrl implements Initializable, languageSwitchInterface {

    private MainCtrl mc;

    @FXML
    private TextField TextFieldName;
    @FXML
    private TextField TextFieldEmail;
    @FXML
    private TextField TextFieldIBAN;
    @FXML
    private TextField TextFieldBIC;

    @FXML
    private ComboBox<String> comboBoxParticipants;
    //    here must go an array with names of participants
    private String[] names = {"John", "Chris", "Anna"};

    @Inject
    public EditParticipantCtrl(MainCtrl mc) {
        this.mc = mc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxParticipants.getItems().addAll(names);
    }


    public void check() {
//        TextFieldMoney.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("\\d*\\.?\\d*")) { // Regex to allow digits and optional decimal point
//                TextFieldMoney.setText(oldValue); // Revert to the old value if new value doesn't match
//            }
//        });
    }

    public void clickBack() {
        mc.showEventOverview();
    }

    public void getAllInfo() {
        String name = TextFieldName.getText();
        String email = TextFieldEmail.getText();
        String iban = TextFieldIBAN.getText();
        String bic = TextFieldBIC.getText();
        Participant meta = new Participant(null, name, email, iban, bic);
        System.out.println(meta.toString());
    }

    @Override
    public void langueageswitch(String taal) {

    }}