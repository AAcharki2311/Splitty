package client.scenes;

import client.utils.EventServerUtils;
import client.utils.ReadJSON;
import client.utils.languageSwitchInterface;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdminLoginCtrl implements Initializable, languageSwitchInterface {

    /** BASIS **/
    private final EventServerUtils server;
    private final MainCtrl mc;
    /** MENU **/
    @FXML
    private ImageView imgSet;
    @FXML
    private ImageView imgArrow;
    @FXML
    private ImageView imgHome;
    /** NEEDED FOR LANGUAGE SWITCH **/
    private final ReadJSON jsonReader;
    private List<String> languages = new ArrayList<>(Arrays.asList("Dutch", "English", "French"));
    @FXML
    private ImageView imageviewFlag;
    @FXML
    private ComboBox comboboxLanguage;
    /** PAGE **/
    @FXML
    private PasswordField inputpw;
    @FXML
    private Label pwtext;
    private final String pw;
    @FXML
    private ImageView imageview;

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

            mc.showAdminLogin();
        });
        Image image = new Image("images/logo-no-background.png");
        imageview.setImage(image);

        imgSet.setImage(new Image("images/settings.png"));
        imgArrow.setImage(new Image("images/arrow.png"));
        imgHome.setImage(new Image("images/home.png"));
    }

    /**
     * Constructer for the AdminLogin Controller
     * @param mc the main controller
     */
    @Inject
    public AdminLoginCtrl(EventServerUtils server, MainCtrl mc, ReadJSON jsonReader) {
        this.mc = mc;
        this.pw = RandomStringUtils.random(8, true, true);
        this.jsonReader = jsonReader;
        this.server = server;
        System.out.println("Your random password is: " + pw);
    }

    /**
     * Used to return to the Start Screen
     */
    public void clickStart () {
        mc.showStart();
    }

    /**
     * Used to login to the admin environment. It checks if the given password is equal to the password in
     * the server output. If it isn't a message gets displayed.
     */
    public void clickLogin() {
        mc.showAdminDashboard();
        String input = inputpw.getText();
        if (input.equals(pw)){
            mc.showAdminDashboard();
        }
        else {
            pwtext.setText("Password is incorrect, please try again");
        }
    }

    /**
     * Javadoc
     * @param taal the language that the user wants to switch to
     * @throws NullPointerException
     */
    @Override
    public void langueageswitch(String taal) throws NullPointerException{
        String langfile = "language" + taal + ".json";
        HashMap<String, Object> h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        comboboxLanguage.setPromptText("Current language: " + taal);
//        welcometext.setText(h.get("key1").toString());
//        pleasetext.setText(h.get("key2").toString());
//        joinBTN.setText(h.get("key3").toString());
//        createBTN.setText(h.get("key4").toString());
//        loginBTN.setText(h.get("key5").toString());
//        recentviewedtext.setText(h.get("key6").toString());
        Image imageFlag = new Image(h.get("key0").toString());
        imageviewFlag.setImage(imageFlag);
    }
}
