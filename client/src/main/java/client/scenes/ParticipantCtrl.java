package client.scenes;

import client.utils.languageSwitchInterface;
import commons.Participant;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class ParticipantCtrl implements languageSwitchInterface {

    private MainCtrl mc;

    @FXML
    private TextField TextFieldName;
    @FXML
    private TextField TextFieldEmail;
    @FXML
    private TextField TextFieldIBAN;
    @FXML
    private TextField TextFieldBIC;

    @Inject
    public ParticipantCtrl(MainCtrl mc) {
        this.mc = mc;
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