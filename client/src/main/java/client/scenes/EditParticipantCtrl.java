package client.scenes;

import client.utils.LanguageSwitchInterface;
import commons.Participant;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditParticipantCtrl implements Initializable, LanguageSwitchInterface {

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

    /**
     * Constructor of the EditParticipantCtrl
     * @param mc represent the MainCtrl
     */
    @Inject
    public EditParticipantCtrl(MainCtrl mc) {
        this.mc = mc;
    }

    /**
     * This methods sets the image for the imageview and adds the items to the comboboxes
     * @param url represent the URL
     * @param resourceBundle represent the ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxParticipants.getItems().addAll(names);
    }

    /**
     * This method checks if the input is correct
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
     * Method of the save button, it gets all user input and when pressed, it shows the eventoverview screen
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