package client.scenes;

import client.utils.LanguageSwitchInterface;
import commons.Participant;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class ParticipantCtrl implements LanguageSwitchInterface {

    private MainCtrl mc;

    @FXML
    private TextField TextFieldName;
    @FXML
    private TextField TextFieldEmail;
    @FXML
    private TextField TextFieldIBAN;
    @FXML
    private TextField TextFieldBIC;

    /**
     * Constructor of the ParticipantCtrl
     * @param mc represent the MainCtrl
     */
    @Inject
    public ParticipantCtrl(MainCtrl mc) {
        this.mc = mc;
    }

    /**
     * This method checks if the input is a valid number
     */
    public void check() {
//        TextFieldMoney.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("\\d*\\.?\\d*")) { // Regex to allow digits and optional decimal point
//                TextFieldMoney.setText(oldValue); // Revert to the old value if new value doesn't match
//            }
//        });
    }

    /**
     * Method of the cancel button, when pressed, it shows the eventoverview screen
     */
    public void clickBack() {
        // mc.showEventOverview();
    }

    /**
     * This method gets all the information from the textfields and prints it to the console
     */
    public void getAllInfo() {
        String name = TextFieldName.getText();
        String email = TextFieldEmail.getText();
        String iban = TextFieldIBAN.getText();
        String bic = TextFieldBIC.getText();
        Participant meta = new Participant(null, name, email, iban, bic);
        System.out.println(meta.toString());
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language that the user wants to switch to
     */
    @Override
    public void langueageswitch(String taal) {

    }}