package client.scenes;

import client.utils.EventServerUtils;
import client.utils.languageSwitchInterface;
import commons.Event;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import client.utils.ReadJSON;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class StartScreenCtrl implements Initializable, languageSwitchInterface {
    @FXML
    private ComboBox comboboxLanguage;
    private final EventServerUtils server;
    private List<String> languages = new ArrayList<>(Arrays.asList("Dutch", "English", "French"));
    private final MainCtrl mc;
    private final ReadJSON jsonReader;
    @FXML
    private ImageView imageview;
    @FXML
    private ImageView imageviewFlag;
    @FXML
    private ImageView imgSet;
    @FXML
    private ImageView imgArrow;
    @FXML
    private ImageView imgHome;
    @FXML
    private Text welcometext;
    @FXML
    private Text pleasetext;
    @FXML
    private Text recentviewedtext;
    @FXML
    private Button joinBTN;
    @FXML
    private Button createBTN;
    @FXML
    private Button loginBTN;
    @FXML
    private TextField eventName;
    @FXML
    private Text message;

    /**
     * This method sets the image for the imageview and adds the items to the comboboxes
     * @param url represent the URL
     * @param resourceBundle represent the ResourceBundle
     * When pressed on the combobox of languages, it translates it immeditalty
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboboxLanguage.getItems().addAll(languages);
        comboboxLanguage.setOnAction(event -> {
            languageChange(comboboxLanguage);
            comboboxLanguage.setPromptText("Current language: " + comboboxLanguage.getSelectionModel().getSelectedItem());

            try {
                mc.ltest();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            mc.showStart();
        });
        Image image = new Image("images/logo-no-background.png");
        imageview.setImage(image);

        imgSet.setImage(new Image("images/settings.png"));
        imgArrow.setImage(new Image("images/arrow.png"));
        imgHome.setImage(new Image("images/home.png"));
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language that the user wants to switch to
     */
    @Override
    public void langueageswitch(String taal) throws NullPointerException{
        String langfile = "language" + taal + ".json";
        HashMap<String, Object> h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        comboboxLanguage.setPromptText("Current language: " + taal);
        welcometext.setText(h.get("key1").toString());
        pleasetext.setText(h.get("key2").toString());
        joinBTN.setText(h.get("key3").toString());
        createBTN.setText(h.get("key4").toString());
        loginBTN.setText(h.get("key5").toString());
        recentviewedtext.setText(h.get("key6").toString());
        Image imageFlag = new Image(h.get("key0").toString());
        imageviewFlag.setImage(imageFlag);
    }


    /**
     * Constructor of the StartScreenCtrl
     * @param mc represent the MainCtrl
     * @param jsonReader is an instance of the ReadJSON class, so it can read JSONS
     * @param server server
     */
    @Inject
    public StartScreenCtrl(EventServerUtils server, MainCtrl mc, ReadJSON jsonReader) {
        this.mc = mc;
        this.jsonReader = jsonReader;
        this.server = server;
    }

    /**
     * Method of the cancel button, when pressed, it shows the eventoverview screen
     */
    public void clickEvent() {
        try {
            String name = eventName.getText();
            Event test = new Event(name);
            System.out.println("New event created: " + test.getId() + " " + test.getName());
            server.addEvent(test);
            mc.showEventOverview(test.getId());
        }
        catch (Exception e){
            message.setText("Name cannot be empty");
        }
    }

    /**
     * Method of the show admin button, when pressed, it shows the showAdminLogin screen
     */
    public void clickAdmin() {
        mc.showAdminLogin();
    }

    /**
     * Javadoc
     */
    public void clickBack(){
        // does nothing as Start Screen has no back
    }

    /**
     * Javadoc
     */
    public void clickHome(){
        mc.showStart();
    }

    /**
     * Javadoc
     */
    public void clickSettings(){
        // mc.showSettings();
        mc.showAdminLogin();
    }

    /**
     * Method of the download template text, when pressed, it should download the template file
     */
    public void downloadTemplate() {
        try {
            URL url = new URL("src/main/java/client/utils/languageTemplate.json");


//            downloadFile(fileUrl, destinationPath);
        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            String error = "Please restart the application and try again later.";
            errorAlert.setHeaderText("Error while downloading file.");
            errorAlert.setContentText(error);
            errorAlert.showAndWait();
        }
    }

}