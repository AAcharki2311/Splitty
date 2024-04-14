package client.scenes;

import client.utils.EventServerUtils;
import client.utils.ParticipantsServerUtil;
import client.utils.ReadJSON;
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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EditParticipantCtrl implements Initializable {
    /** INIT **/
    private final MainCtrl mc;
    private final ReadJSON jsonReader;
    private final EventServerUtils server;
    private final ParticipantsServerUtil partServer;
    private long eventid;
    private boolean ibanTrue;
    private Participant selectedParticipant;
    private HashMap<String, String> h;
    /**
     * MENU
     **/
    @FXML
    private ImageView imgSet;
    @FXML
    private ImageView imgArrow;
    @FXML
    private ImageView imgHome;
    @FXML
    private ImageView imageviewFlag;
    /** PAGE FXML **/
    @FXML
    private ImageView imageview;
    @FXML
    private Text changeTheParticipantOfText;
    @FXML
    private Text titleOfScreen;
    @FXML
    private Text fillInfoText;
    @FXML
    private Label nameText;
    @FXML
    private Text labelEventName;
    @FXML
    private TextField TextFieldName;
    @FXML
    private TextField TextFieldEmail;
    @FXML
    private TextField TextFieldIBAN;
    @FXML
    private TextField TextFieldBIC;
    @FXML
    private Text message;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button deleteBtn;
    /** Combobox with Participant Info **/
    @FXML
    private ComboBox<String> comboBoxParticipants;
    private ArrayList<String> names = new ArrayList<>();

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
        imageview.setImage(new Image("images/logo-no-background.png"));
        imageviewFlag.setImage(new Image("images/br-circle-01.png"));
        imgSet.setImage(new Image("images/settings.png"));
        imgArrow.setImage(new Image("images/arrow.png"));
        imgHome.setImage(new Image("images/home.png"));

        comboBoxParticipants.setOnAction(event -> {
            String nameParticipant = comboBoxParticipants.getValue();
            if(nameParticipant == null){
                message.setText(h.get("key83"));
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
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language that the user wants to switch to
     */
    public void langueageswitch(String taal) {
        String langfile = "language" + taal + ".json";
        h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        titleOfScreen.setText(h.get("key28"));
        changeTheParticipantOfText.setText(h.get("key29"));
        fillInfoText.setText(h.get("key30"));
        nameText.setText(h.get("key31"));
        cancelBtn.setText(h.get("key26"));
        deleteBtn.setText(h.get("key40"));
        comboBoxParticipants.setPromptText(h.get("key7"));
        TextFieldName.setPromptText(h.get("key31"));
        Image imageFlag = new Image(h.get("key0"));
        imageviewFlag.setImage(imageFlag);
    }

    /**
     * Method of the save button, it gets all user input and when pressed, it shows the eventoverview screen
     */
    public void submitEdit() {
        String errormessage = h.get("key80");
        try{
            if(comboBoxParticipants.getValue() == null){
                errormessage = h.get("key83");
                throw new Exception();
            }
            var e = server.getEventByID(eventid);
            String name = TextFieldName.getText();
            String email = TextFieldEmail.getText();
            String iban = TextFieldIBAN.getText();
            String bic = TextFieldBIC.getText();

            boolean duplicate = checkDuplicate(name, email);
            if(validate(name, email, iban, bic) && !duplicate){
                Participant newParticipant = new Participant(e, name, email, iban, bic);

                int choice = JOptionPane.showOptionDialog(null,h.get("key85"), h.get("key86"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        new String[]{"Update", h.get("key26")}, "default");

                if(choice == JOptionPane.OK_OPTION){
                    partServer.updateParticipantByID(selectedParticipant.getId(),newParticipant);
                    newParticipant.setId(selectedParticipant.getId());
                    mc.send("/app/events/"+eventid+"/participants", newParticipant);
                    String message = h.get("key31") + "136:\n" +
                            "_______________" + "\n" +
                            h.get("key31") + ": " + newParticipant.getName() + "\n" +
                            "Email: " + newParticipant.getEmail() + "\n" +
                            "IBAN: " + newParticipant.getIban() + "\n" +
                            "BIC: " + newParticipant.getBic();
                    JOptionPane.showMessageDialog(null, message);
                    clickBack();
                }
            } else {
                if(!(ibanTrue)) errormessage = h.get("key78");
                if(!(email.contains("@") && email.contains(".")))  errormessage = h.get("key84");
                JOptionPane.showOptionDialog(null, errormessage,h.get("key114"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{}, null);
            }
        } catch (Exception e){
            JOptionPane.showOptionDialog(null, h.get("key115"),h.get("key116"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, new Object[]{}, null);
            // message.setText(errormessage);
        }
    }

    /**
     * This method checks if the name + email is a duplicate
     * @param name the name of the participant
     * @param email the email of the participant
     * @return true if it is a duplicate, false if it is not a duplicate
     */
    public boolean checkDuplicate(String name, String email){
        List<Participant> allParticipants = partServer.getAllParticipants()
                .stream().filter(participant -> participant.getEvent().getId() == eventid)
                .collect(Collectors.toList());
        allParticipants.remove(selectedParticipant);
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

        List<Participant> listAllParticipants = partServer.getAllParticipants()
                .stream().filter(participant -> participant.getEvent().getId() == eventid).collect(Collectors.toList());
        names = (ArrayList<String>) listAllParticipants.stream().map(Participant::getName).collect(Collectors.toList());
        comboBoxParticipants.getItems().clear();
        comboBoxParticipants.getItems().addAll(names);
    }

    /**
     * This method sets all fields to the information of the participant
     * @param participant the participant
     */
    public void setComboBoxParticipants(Participant participant){
        comboBoxParticipants.setValue(participant.getName());
        TextFieldName.setText(participant.getName());
        TextFieldEmail.setText(participant.getEmail());
        TextFieldIBAN.setText(participant.getIban());
        TextFieldBIC.setText(participant.getBic());
    }

    /**
     * Method of the delete button, when pressed, it deletes the participant
     */
    public void delete(){
        String name = comboBoxParticipants.getValue();
        if(name == null){
            message.setText(h.get("key83"));
            return;
        }

        int choice = JOptionPane.showOptionDialog(null,h.get("key82"), h.get("key66"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
                new String[]{h.get("key67"), h.get("key26")}, "default");

        if(choice == JOptionPane.OK_OPTION){
            partServer.deleteParticipantByID(selectedParticipant.getId());
            JOptionPane.showMessageDialog(null, h.get("key81"));
            update(String.valueOf(eventid));
            setEverythingEmpty();
        }
    }

    /**
     * This method sets all fields to empty
     */
    public void setEverythingEmpty(){
        comboBoxParticipants.setValue(null);
        TextFieldName.setText(null);
        TextFieldEmail.setText(null);
        TextFieldIBAN.setText(null);
        TextFieldBIC.setText(null);
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
        mc.help(h);
    }

    /**
     * Used to get back to the Start Screen
     * @throws IOException if something went wrong with showing the start screen
     */
    public void clickHome() throws IOException {
        mc.showStart();
    }


}