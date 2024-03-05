package client.scenes;

import client.utils.ReadJSON;
import jakarta.inject.Inject;
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

            mc.showEventOverview();
        });
    }

    @Override
    public void langueageswitch(String taal) {
        HashMap<String, Object> h = jsonReader.readJsonToMap("C:\\Users\\ayoub\\oopp-ayoubacharki\\TEAM\\oopp-team-23\\client\\src\\main\\resources\\languageJSONS\\language" + taal + ".json");
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

    @Inject
    public EventOverviewCtrl(MainCtrl mc, ReadJSON jsonReader) {
        this.mc = mc;
        this.jsonReader = jsonReader;
    }
    public void clickInvite() {
        mc.showInvite();
    }

    public void clickBack() {
        mc.showStart();
    }

    public void clickAddExpense() {
        mc.showExpense();
    }

    public void clickAddParticipant() {
        mc.showParticipant();
    }

    public void clickEditParticipant() {
        mc.showEditParticipant();
    }

    public void clickEditExpense() {
        mc.showEditExpense();
    }


}