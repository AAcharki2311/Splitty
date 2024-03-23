package client.scenes;

import client.utils.EventServerUtils;
import client.utils.ParticipantsServerUtil;
import client.utils.ReadJSON;
import client.utils.LanguageSwitchInterface;
import commons.Participant;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EditParticipantCtrl implements Initializable, LanguageSwitchInterface {
    private final MainCtrl mc;
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
    private Label changeTheParticipantOfText;
    @FXML
    private Label titleOfScreen;
    @FXML
    private Label fillInfoText;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Label nameText;
    @FXML
    private ComboBox<String> comboBoxParticipants;
    //    here must go an array with names of participants
    private ArrayList<String> names = new ArrayList<>();
    private final EventServerUtils server;
    private final ParticipantsServerUtil partServer;
    private long eventid;
    @FXML
    private Label labelEventName;

    /**
     * Constructor of the EditParticipantCtrl
     * @param server represent the EventServerUtils
     * @param mc represent the MainCtrl
     * @param jsonReader represent the ReadJSON
     * @param partServer represent the ParticipantsServerUtil
     */
    @Inject
    public EditParticipantCtrl(EventServerUtils server, MainCtrl mc, ReadJSON jsonReader, ParticipantsServerUtil partServer) {
        this.mc = mc;
        this.jsonReader = jsonReader;
        this.server = server;
        this.partServer = partServer;
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
     * Method of the cancel button, when pressed, it shows the eventoverview screen
     */
    public void clickBack() {
        mc.showEventOverview(String.valueOf(eventid));
    }

    /**
     * Method of the save button, it gets all user input and when pressed, it shows the eventoverview screen
     */
    public void submitEdit() {
        try{
            var e = server.getEventByID(eventid);
            String name = TextFieldName.getText();
            String email = TextFieldEmail.getText();
            String iban = TextFieldIBAN.getText();
            String bic = TextFieldBIC.getText();
            if(validate(name, email, iban, bic)){
                Participant p = new Participant(e, name, email, iban, bic);
                System.out.println("New Participant added: " +
                        p.getName() + " " +
                        p.getEmail() + " " +
                        p.getIban() + " " +
                        p.getBic());
                clickBack();
            } else {
                throw new Exception("Exception message");
            }
        } catch (Exception e){
            System.out.println("Something went wrong");
        }
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
        if(name.isBlank() || email.isBlank() || iban.isBlank() || bic.isBlank() ||
                !email.matches(".*@.+\\..+") ||
                !iban.matches("^[a-zA-Z]{2}.*") ||
                !bic.matches("^[a-zA-Z]{6}.*")){
            return false;
        }
        return true;
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language that the user wants to switch to
     */
    @Override
    public void langueageswitch(String taal) {
        String langfile = "language" + taal + ".json";
        HashMap<String, Object> h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        titleOfScreen.setText(h.get("key28").toString());
        changeTheParticipantOfText.setText(h.get("key29").toString());
        fillInfoText.setText(h.get("key30").toString());
        nameText.setText(h.get("key31").toString());
        cancelBtn.setText(h.get("key26").toString());
        deleteBtn.setText(h.get("key40").toString());
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

        List<Participant> listAllParticipants = partServer.getAllParticipants()
                .stream().filter(participant -> participant.getEvent().getId() == eventid).collect(Collectors.toList());
        names.clear();
        for (Participant p : listAllParticipants) {
            names.add(p.getName());
        }
        comboBoxParticipants.getItems().clear();
        comboBoxParticipants.getItems().addAll(names);
    }

    /**
     * Method of the delete button, when pressed, it deletes the expense
     */
    public void delete(){
        System.out.println("Delete button pressed");
    }
}