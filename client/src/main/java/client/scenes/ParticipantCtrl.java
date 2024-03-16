package client.scenes;

import client.utils.ReadJSON;

import client.utils.LanguageSwitchInterface;
import commons.Participant;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * This class is the controller for the Participant screen. It is used to add a participant to an event
 */
public class ParticipantCtrl implements Initializable, LanguageSwitchInterface {

    private MainCtrl mc;
    private final ReadJSON jsonReader;
    @FXML
    private ImageView imageview;
    @FXML
    private TextField TextFieldName;
    @FXML
    private TextField TextFieldEmail;
    @FXML
    private TextField TextFieldIBAN;
    @FXML
    private TextField TextFieldBIC;
    @FXML
    private Label titleOfScreen;
    @FXML
    private Label fillInfoText;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label nameText;

    /**
     * Constructor of the AddParticipantCtrl
     * @param mc represent the MainCtrl
     * @param jsonReader represent the ReadJSON
     */
    @Inject
    public ParticipantCtrl(MainCtrl mc, ReadJSON jsonReader) {
        this.mc = mc;
        this.jsonReader = jsonReader;
    }

    /**
     * This method sets the image for the imageview and adds the items to the comboboxes
     * @param url represent the URL
     * @param resourceBundle represent the ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("images/logo-no-background.png");
        imageview.setImage(image);
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
    public void submit() {
        String name = TextFieldName.getText();
        String email = TextFieldEmail.getText();
        String iban = TextFieldIBAN.getText();
        String bic = TextFieldBIC.getText();
        Participant meta = new Participant(null, name, email, iban, bic);
        System.out.println(meta.toString());
//        mc.showEventOverview();
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language that the user wants to switch to
     */
    @Override
    public void langueageswitch(String taal) {
        String langfile = "language" + taal + ".json";
        HashMap<String, Object> h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        titleOfScreen.setText(h.get("key32").toString());
        fillInfoText.setText(h.get("key30").toString());
        nameText.setText(h.get("key31").toString());
        cancelBtn.setText(h.get("key26").toString());
    }
}