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
import javafx.scene.text.Text;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.*;
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
    private ArrayList<String> names = new ArrayList<>();
    private final EventServerUtils server;
    private final ParticipantsServerUtil partServer;
    private long eventid;
    @FXML
    private Label labelEventName;
    @FXML
    private Text message;
    private Participant selectedParticipant;

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

        comboBoxParticipants.setOnAction(event -> {
            String nameParticipant = comboBoxParticipants.getValue();
            if(nameParticipant == null){
                message.setText("No participant selected");
                return;
            }

            List<Participant> listAllParticipants = partServer.getAllParticipants()
                    .stream().filter(participant -> participant.getEvent().getId() == eventid).collect(Collectors.toList());

            selectedParticipant = listAllParticipants.stream().filter(participant -> participant.getName().equals(nameParticipant)).findAny().get();

            TextFieldName.setText(selectedParticipant.getName());
            TextFieldEmail.setText(selectedParticipant.getEmail());
            TextFieldIBAN.setText(selectedParticipant.getIban());
            TextFieldBIC.setText(selectedParticipant.getBic());
        });

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
        if(comboBoxParticipants.getValue() == null){
            message.setText("No participant selected");
            return;
        }
        try{
            var e = server.getEventByID(eventid);
            String name = TextFieldName.getText();
            String email = TextFieldEmail.getText();
            String iban = TextFieldIBAN.getText();
            String bic = TextFieldBIC.getText();

            if(validate(name, email, iban, bic)){
                Participant newParticipant = new Participant(e, name, email, iban, bic);

                int choice = JOptionPane.showOptionDialog(null,"Are you sure you want to update?", "Update Confirmation",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        new String[]{"Update", "Cancel"}, "default");

                if(choice == JOptionPane.OK_OPTION){
                    partServer.updateParticipantByID(selectedParticipant.getId(),newParticipant);
                    JOptionPane.showMessageDialog(null, "Participant Updated");
                    clickBack();
                } else{
                    System.out.println("Operation cancelled by the user. Participant remains unchanged.");
                }
            } else {
                message.setText("Please fill in all fields correctly");
                throw new Exception();
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
                !email.matches(".*@.+\\..+")){
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
     * Method of the delete button, when pressed, it deletes the participant
     */
    public void delete(){
        String name = comboBoxParticipants.getValue();

        if(name == null){
            message.setText("No participant selected");
            return;
        }
        int choice = JOptionPane.showOptionDialog(null,"Are you sure you want to delete?", "Delete Confirmation",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
                new String[]{"Delete", "Cancel"}, "default");

        if(choice == JOptionPane.OK_OPTION){
            Optional<Participant> p = partServer.getAllParticipants()
                    .stream().filter(participant -> participant.getName().equals(name)).findAny();
            partServer.deleteParticipantByID(p.get().getId());

            JOptionPane.showMessageDialog(null, "Participant deleted");
            update(String.valueOf(eventid));
        } else{
            System.out.println("Operation cancelled by the user. Participant remains unchanged.");
        }
    }
}