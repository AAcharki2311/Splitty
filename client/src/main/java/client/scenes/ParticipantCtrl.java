package client.scenes;

import client.utils.EventServerUtils;
import client.utils.ParticipantsServerUtil;
import client.utils.ReadJSON;

import client.utils.LanguageSwitchInterface;
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

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ParticipantCtrl implements Initializable, LanguageSwitchInterface {
    /** INIT **/
    private final MainCtrl mc;
    private final ReadJSON jsonReader;
    private final EventServerUtils server;
    private final ParticipantsServerUtil pcu;
    private long eventid;
    private Participant userParticipant;
    /** PAGE FXML **/
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
    private Label nameText;
    @FXML
    private Label labelEventName;
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
        Image image = new Image("images/logo-no-background.png");
        imageview.setImage(image);
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

    /**
     * This method gets all the information from the textfields and prints it to the console
     */
    public void submit() {
        String errormessage = "Something went wrong";
        try{
            Event e = server.getEventByID(eventid);
            String name = TextFieldName.getText();
            String email = TextFieldEmail.getText();
            String iban = TextFieldIBAN.getText();
            String bic = TextFieldBIC.getText();
            boolean duplicate = checkDuplicate(name, email);
            if(validate(name, email, iban, bic) && !duplicate){
                Participant p = new Participant(e, name, email, iban, bic);
                p.setEvent(e);
                pcu.addParticipant(p);
                clickBack();
            } else {
                if(duplicate){
                    errormessage = "Name or email already exists";
                } else{
                    errormessage = "Please fill in all fields correctly";
                }
                throw new Exception();
            }
        } catch (Exception e){
            message.setText(errormessage);
        }
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
        boolean ibanTrue = iban.matches("^[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{1,30}$");
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
        System.out.println("reached");
        System.out.println(eid + " " + server.getEventByID(eid).getName());
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
     * Method of the cancel button, when pressed, it shows the eventoverview screen
     */
    public void clickBack() throws IOException {
        mc.showEventOverview(String.valueOf(eventid));
    }
}