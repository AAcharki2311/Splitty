package client.scenes;

import client.utils.EventServerUtils;
import client.utils.ReadJSON;
import client.utils.LanguageSwitchInterface;
import commons.Event;
import jakarta.inject.Inject;
// import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

import java.util.*;

public class EventOverviewCtrl implements Initializable, LanguageSwitchInterface {
    private final MainCtrl mc;
    private final ReadJSON jsonReader;
    @FXML
    private ComboBox comboboxLanguage;
    private List<String> languages = new ArrayList<>(Arrays.asList("dutch", "english", "french"));
    @FXML
    private ImageView imageviewFlag;
    @FXML
    private Label partictext;
    @FXML
    private Label expenstext;
    @FXML
    private Label showExpensOfText;
    @FXML
    private Label particNameLabel;
    @FXML
    private Button editBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn1;
    @FXML
    private Button addBtn1;
    @FXML
    private Button settleDebtsBtn;
    @FXML
    private Button sendInviteBtn;
    @FXML
    private Button allBtn;
    @FXML
    private Button fromNameBtn;
    @FXML
    private Button includingNameBtn;
    @FXML
    private ImageView imageview;
    @FXML
    private ComboBox<String> comboBoxOne;
    private String[] names = {"John", "Chris", "Anna"}; //Names of the participants
    @FXML
    private Label eventName;
    private final EventServerUtils server;
    private long eventid;
    @FXML
    private Label editEventNameLabel;

    /**
     * Constructor of the EventoverviewCtrl
     * @param mc represent the MainCtrl
     * @param jsonReader is an instance of the ReadJSON class, so it can read JSONS
     * @param server server
     */
    @Inject
    public EventOverviewCtrl(EventServerUtils server, MainCtrl mc, ReadJSON jsonReader) {
        this.mc = mc;
        this.jsonReader = jsonReader;
        this.server = server;
    }

    /**
     * This method sets the image for the imageview and adds the items to the comboboxes
     * @param url represent the URL
     * @param resourceBundle represent the ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxOne.getItems().addAll(names);
        Image image = new Image("images/logo-no-background.png");
        imageview.setImage(image);
        comboboxLanguage.getItems().addAll(languages);
        comboboxLanguage.setOnAction(event -> {
            languageChange(comboboxLanguage);
            comboboxLanguage.setPromptText("Current language: " + comboboxLanguage.getSelectionModel().getSelectedItem());

            try {
                mc.ltest();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mc.showEventOverview(Long.toString(eventid));
        });
        server.registerForEventUpdates("topic/events", q -> {
            System.out.println("Recieved to add:"+q.toString());
        });
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language that the user wants to switch to
     */
    @Override
    public void langueageswitch(String taal) {
        String langfile = "language" + taal + ".json";
        HashMap<String, Object> h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        comboboxLanguage.setPromptText("Current language: " + taal);
        Image imageFlag = new Image(h.get("key0").toString());
        imageviewFlag.setImage(imageFlag);

        partictext.setText(h.get("key7").toString());
        expenstext.setText(h.get("key8").toString());
        editBtn.setText(h.get("key9").toString());
        addBtn.setText(h.get("key10").toString());
        editBtn1.setText(h.get("key9").toString());
        addBtn1.setText(h.get("key10").toString());
        settleDebtsBtn.setText(h.get("key11").toString());
        sendInviteBtn.setText(h.get("key12").toString());
        allBtn.setText(h.get("key13").toString());
        fromNameBtn.setText(h.get("key14").toString());
        includingNameBtn.setText(h.get("key15").toString());
        showExpensOfText.setText(h.get("key37").toString());
        editEventNameLabel.setText(h.get("key38").toString());
    }

    /**
     * Method of the invite button, when pressed, it shows the invite screen
     */
    public void clickInvite() {
        mc.showInvite(String.valueOf(eventid));
    }

    /**
     * Method of the cancel button, when pressed, it shows the eventoverview screen
     */
    public void clickBack() {
        mc.showStart();
    }

    /**
     * Method of the add button, when pressed, it shows the add expense screen
     */
    public void clickAddExpense() {
        mc.showExpense(String.valueOf(eventid));
    }

    /**
     * Method of the add button, when pressed, it shows the add participant screen
     */
    public void clickAddParticipant() {
        mc.showParticipant(String.valueOf(eventid));
    }

    /**
     * Method of the edit button, when pressed, it shows the edit participant screen
     */
    public void clickEditParticipant() {
        mc.showEditParticipant(String.valueOf(eventid));
    }

    /**
     * Method of the edit button, when pressed, it shows the edit expense screen
     */
    public void clickEditExpense() {
        mc.showEditExpense(String.valueOf(eventid));
    }

    /**
     * Method of the edit event name button, when pressed, it shows the edit event name dialog
     */
    public void editEventName(){
        String oldName = eventName.getText();
        String newName = "";

        while(true){
            newName = JOptionPane.showInputDialog("Enter the new name of the event");
            if (newName == null || newName.equals(oldName)) {
                System.out.println("Operation cancelled by the user. Name remains unchanged.");
                return;
            } else if (newName.isBlank()) {
                JOptionPane.showMessageDialog(null, "Name cannot be empty.");
            } else if(checkDuplicateName(newName)){
                JOptionPane.showMessageDialog(null, "Not allowed. Duplicate name detected.");
            } else {
                Event x = server.getEventByID(eventid);
                x.setName(newName);
                server.updateEventByID(eventid, x);

                eventName.setText(server.getEventByID(eventid).getName());
                System.out.println("Name of event(id " + eventid + ") changed from " + oldName + " to " + newName);
                break;
            }
        }
    }

    /**
     * This method checks if the name is a duplicate
     * @param name the name of the event
     * @return true if the name is a duplicate, false if the name is not a duplicate
     */
    public boolean checkDuplicateName(String name){
        List<Event> allEvents = server.getAllEvents();
        List<String> namesOfAllEvents = new ArrayList<>();
        for(Event e : allEvents){ namesOfAllEvents.add(e.getName());}
        return namesOfAllEvents.contains(name);
    }

    /**
     * Updates the page with the right information
     * @param id the id of the event
     */
    public void update(String id){
        long eid = Long.parseLong(id);
        this.eventid = eid;
        Event x = server.getEventByID(eid);
        System.out.println("reached");
        System.out.println(x.getId() + " " + x.getName());
        eventName.setText(x.getName());

        String particText = "";
        for(String name : names){
            particText += name + ", ";
        }
        particNameLabel.setText(particText);
    }
}