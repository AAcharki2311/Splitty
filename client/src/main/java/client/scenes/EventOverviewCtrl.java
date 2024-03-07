package client.scenes;

import client.utils.EventServerUtils;
import client.utils.ReadJSON;
import client.utils.languageSwitchInterface;
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

import java.io.IOException;
import java.net.URL;

import java.util.*;

public class EventOverviewCtrl implements Initializable, languageSwitchInterface {

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
    private String[] names = {"John", "Chris", "Anna"};
    @FXML
    private Label eventName;

    private final EventServerUtils server;

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

            // mc.showEventOverview(id);
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
    }

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
     * Method of the invite button, when pressed, it shows the invite screen
     */
    public void clickInvite() {
        mc.showInvite();
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
        mc.showExpense();
    }

    /**
     * Method of the add button, when pressed, it shows the add participant screen
     */
    public void clickAddParticipant() {
        mc.showParticipant();
    }

    /**
     * Method of the edit button, when pressed, it shows the edit participant screen
     */
    public void clickEditParticipant() {
        mc.showEditParticipant();
    }

    /**
     * Method of the edit button, when pressed, it shows the edit expense screen
     */
    public void clickEditExpense() {
        mc.showEditExpense();
    }

    /**
     * Updates the page with the right information
     * @param id
     */
    public void update(long id){
        Event x = server.getEventByID(id);
        System.out.println("reached");
        System.out.println(x.getId() + " " + x.getName());
        eventName.setText(x.getName());
    }
}