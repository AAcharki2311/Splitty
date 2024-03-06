package client.scenes;

import client.utils.languageSwitchInterface;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import client.utils.ReadJSON;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class StartScreenCtrl implements Initializable, languageSwitchInterface {
    @FXML
    private ComboBox comboboxLanguage;
    private List<String> languages = new ArrayList<>(Arrays.asList("dutch", "english", "french"));
    private final MainCtrl mc;
    private final ReadJSON jsonReader;
    @FXML
    private ImageView imageview;
    @FXML
    private ImageView imageviewFlag;
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
    }
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

    @Inject
    public StartScreenCtrl(MainCtrl mc, ReadJSON jsonReader) {
        this.mc = mc;
        this.jsonReader = jsonReader;
    }

    public void clickEvent() {
        mc.showEventOverview();
    }

    public void clickAdmin() {
        mc.showAdminLogin();
    }

    public void downloadTemplate() {
        try {
            URL url = new URL("C:\\Users\\ayoub\\oopp-ayoubacharki\\TEAM\\oopp-team-23\\client\\src\\main\\java\\client\\utils\\languageTemplate.json");


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