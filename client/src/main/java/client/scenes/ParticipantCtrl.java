package client.scenes;

import client.utils.EventServerUtils;
import client.utils.ParticipantsServerUtil;
import client.utils.ReadJSON;

import commons.Event;
import commons.Participant;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ParticipantCtrl implements Initializable {
    /** INIT **/
    private final MainCtrl mc;
    private final ReadJSON jsonReader;
    private final EventServerUtils server;
    private final ParticipantsServerUtil pcu;
    private long eventid;
    private boolean ibanTrue;
    private Participant userParticipant;
    private HashMap<String, String> h;
    /** PAGE FXML **/
    @FXML
    private ImageView imageview;
    @FXML
    private ImageView imageviewFlag;
    @FXML
    private ImageView imgSet;
    @FXML
    private ImageView imgHome;
    @FXML
    private ImageView imgArrow;
    @FXML
    private TextField TextFieldName;
    @FXML
    private TextField TextFieldEmail;
    @FXML
    private TextField TextFieldIBAN;
    @FXML
    private TextField TextFieldBIC;
    @FXML
    private Text titleOfScreen;
    @FXML
    private Text fillInfoText;
    @FXML
    private Label nameText;
    @FXML
    private Label fillUserInfo;
    @FXML
    private Text labelEventName;
    @FXML
    private Button cancelBtn;
    @FXML
    private Text message;

    /**
     * Constructor of the AddParticipantCtrl
     * @param server represent the EventServerUtils
     * @param mc represent the MainCtrl
     * @param jsonReader represent the ReadJSON
     * @param pcu represent the ParticipantsServerUtil
     */
    @Inject
    public ParticipantCtrl(EventServerUtils server, MainCtrl mc, ReadJSON jsonReader, ParticipantsServerUtil pcu) {
        this.mc = mc;
        this.jsonReader = jsonReader;
        this.server = server;
        this.pcu = pcu;
    }

    /**
     * This method sets the image for the imageview and adds the items to the comboboxes
     * @param url represent the URL
     * @param resourceBundle represent the ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageview.setImage(new Image("images/logo-no-background.png"));
        imageviewFlag.setImage(new Image("images/br-circle-01.png"));
        imgSet.setImage(new Image("images/settings.png"));
        imgArrow.setImage(new Image("images/arrow.png"));
        imgHome.setImage(new Image("images/home.png"));
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language that the user wants to switch to
     */
    public void langueageswitch(String taal) {
        String langfile = "language" + taal + ".json";
        h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        titleOfScreen.setText(h.get("key32"));
        fillInfoText.setText(h.get("key30"));
        nameText.setText(h.get("key31"));
        cancelBtn.setText(h.get("key26"));
        fillUserInfo.setText(h.get("key51"));
        Image imageFlag = new Image(h.get("key0"));
        imageviewFlag.setImage(imageFlag);
         TextFieldName.setPromptText(h.get("key31"));
    }

    /**
     * This method gets all the information from the textfields and prints it to the console
     */
    public void submit() {
        String errormessage = "Error!";
        try{
            Event e = server.getEventByID(eventid);
            String name = TextFieldName.getText();
            String email = TextFieldEmail.getText();
            String iban = TextFieldIBAN.getText();
            String bic = TextFieldBIC.getText();
            boolean duplicate = checkDuplicate(name, email);
            if(validate(name, email, iban, bic) && !duplicate) {
                Participant p = new Participant(e, name, email, iban, bic);
                p.setEvent(e);
                pcu.addParticipant(p);
                server.send("/app/events/"+eventid, p);
                String message = "Participant:\n" +
                        "_______________" + "\n" +
                        h.get("key31") + ": " + p.getName() + "\n" +
                        "Email: " + p.getEmail() + "\n" +
                        "IBAN: " + p.getIban() + "\n" +
                        "BIC: " + p.getBic();
                JOptionPane.showMessageDialog(null, message);
                clickBack();
            }else {
                errormessage = elsemethod(duplicate, name, email, iban, bic, h);
                throw new Exception();
            }
        } catch (Exception e){
            message.setText(errormessage);
        }
    }

    /**
     * This method is used to check if the input is correct
     * @param duplicate boolean to check if the name + email is a duplicate
     * @param name the name of the participant
     * @param email the email of the participant
     * @param iban the iban of the participant
     * @param bic the bic of the participant
     * @param h the hashmap with the translations
     * @return the error message
     */
    public String elsemethod(boolean duplicate, String name, String email, String iban, String bic, HashMap<String, String> h) {
        if(duplicate){
            return h.get("key75");
        } else if(name.isBlank()){
            return h.get("key76");
        } else if(!(email.contains("@") && email.contains("."))) {
            return h.get("key77");
        } else if(!(ibanTrue)){
            return h.get("key78");
        }else if(bic.isBlank()){
            return h.get("key79");
        }
        return h.get("key80");
    }


    /**
     * This method checks if the name + email is a duplicate
     * @param name the name of the participant
     * @param email the email of the participant
     * @return true if it is a duplicate, false if it is not a duplicate
     */
    public boolean checkDuplicate(String name, String email){
        List<Participant> allParticipants = pcu.getAllParticipants()
                .stream().filter(participant -> participant.getEvent().getId() == eventid)
                .collect(Collectors.toList());
        List<String> namesOfAllParticipants = allParticipants.stream().map(Participant::getName).collect(Collectors.toList());
        List<String> emailsOfAllParticipants = allParticipants.stream().map(Participant::getEmail).collect(Collectors.toList());
        return namesOfAllParticipants.contains(name) || emailsOfAllParticipants.contains(email);
    }

    /**
     * This method checks if the input is correct
     * @param name the name of the participant
     * @param email the email of the participant
     * @param iban the iban of the participant
     * @param bic the bic of the participant
     * @return true if the input is correct, false if the input is incorrect
     */
    public boolean validate(String name, String email, String iban, String bic){
        ibanTrue = iban.matches("^[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{1,30}$");
        return !name.isBlank() && !email.isBlank() && !iban.isBlank() && !bic.isBlank() &&
                email.contains("@") && email.contains(".") && ibanTrue;
    }

    /**
     * Updates the page with the right information
     * @param id the id of the event
     */
    public void update(String id){
        long eid = Long.parseLong(id);
        this.eventid = eid;
        labelEventName.setText(server.getEventByID(eid).getName());
    }

    /**
     * Fills the user information in the text fields
     */
    public void fillUser(){
        if(userParticipant != null){
            TextFieldName.setText(userParticipant.getName());
            TextFieldEmail.setText(userParticipant.getEmail());
            TextFieldIBAN.setText(userParticipant.getIban());
            TextFieldBIC.setText(userParticipant.getBic());
        }
    }

    /**
     * Method to get the user participant
     * @param userParticipant the user participant
     */
    public void setUserParticipant(Participant userParticipant) {
        this.userParticipant = userParticipant;
    }

    /**
     * Method to get the user participant
     * @return the user participant
     */
    public Participant getUserParticipant() {
        return userParticipant;
    }

    /**
     * Method to set the event id
     * @param eventid the event id
     */
    public void setEventid(long eventid) {
        this.eventid = eventid;
    }

    /**
     * Method to get the eventid
     * @return the eventid
     */
    public long getEventid() {
        return eventid;
    }

    /**
     * Method of the cancel button, when pressed, it shows the eventoverview screen
     */
    public void clickBack() {
        mc.showEventOverview(String.valueOf(eventid));
    }

    /**
     * Method of the settings button, when pressed, it shows the keyboard combo's
     */
    public void clickSettings() {
        mc.help();
    }

    /**
     * Used to get back to the Start Screen
     * @throws IOException if something went wrong with showing the start screen
     */
    public void clickHome() throws IOException {
        mc.showStart();
    }
}