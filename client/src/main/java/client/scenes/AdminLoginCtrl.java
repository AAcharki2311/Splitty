package client.scenes;

import client.utils.EventServerUtils;
import client.utils.PasswordServerUtils;
import client.utils.ReadJSON;
import javax.naming.directory.InvalidAttributesException;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javax.swing.*;
import java.net.URL;
import java.util.*;

public class AdminLoginCtrl implements Initializable {

    /**
     * BASIS
     **/
    private final EventServerUtils server;
    private final MainCtrl mc;
    private final PasswordServerUtils pwserver;
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
    /**
     * PAGE
     **/
    @FXML
    private ImageView imgMessage;
    @FXML
    private PasswordField inputpw;
    @FXML
    private Label pwText;
    @FXML
    private Button loginText;
    @FXML
    private Button backText;
    @FXML
    private Text welcomeText;
    @FXML
    private ImageView imageview;
    private final ReadJSON jsonReader;
    private HashMap<String, String> h;

    /**
     * This method sets the image for the imageview and adds the items to the comboboxes
     *
     * @param url            represent the URL
     * @param resourceBundle represent the ResourceBundle
     *                       When pressed on the combobox of languages, it translates it immeditalty
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageview.setImage(new Image("images/logo-no-background.png"));
        imageviewFlag.setImage(new Image("images/br-circle-01.png"));
        imgSet.setImage(new Image("images/settings.png"));
        imgArrow.setImage(new Image("images/arrow.png"));
        imgHome.setImage(new Image("images/home.png"));
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language to switch to
     */
    public void langueageswitch(String taal) throws InvalidAttributesException {
        try {
            String langfile = "language" + taal + ".json";
            h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/" + langfile);
            welcomeText.setText(h.get("key96"));
            backText.setText(h.get("key99"));
            pwText.setText(h.get("key98"));
            loginText.setText(h.get("key97"));
            Image imageFlag = new Image(h.get("key0"));
            imageviewFlag.setImage(imageFlag);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    /**
     * Constructor for the AdminLogin Controller
     *
     * @param server     the server
     * @param mc         the main controller
     * @param pwserver   the password server
     * @param jsonReader
     */
    @Inject
    public AdminLoginCtrl(EventServerUtils server, PasswordServerUtils pwserver, MainCtrl mc, ReadJSON jsonReader) {
        this.mc = mc;
        this.jsonReader = jsonReader;
        this.pwserver = pwserver;
        this.server = server;
    }

    /**
     * Used to return to the Start Screen
     */
    public void clickStart() {
        mc.showStart();
    }

    /**
     * Used to log in to the admin environment. It checks if the given password is equal to the password in
     * the server output. If it isn't a message gets displayed.
     */
    public void clickLogin() {
        // mc.showAdminDashboard();
        String input = inputpw.getText();
        if (!input.isBlank() && pwserver.checkPassword(input)){
            pwText.setText("correct");
            mc.showAdminDashboard();
        } else {
            JOptionPane.showOptionDialog(null, h.get("key117"),h.get("key114"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{}, null);
//            imgMessage.setImage(new Image("images/notifications/Slide1.png"));
//            PauseTransition pause = new PauseTransition(Duration.seconds(6));
//            pause.setOnFinished(p -> imgMessage.setImage(null));
//            pause.play();
            return;
        }
    }

    /**
     * Method of the settings button, when pressed, it shows the keyboard combo's
     */
    public void clickSettings() {
        mc.help(h);
    }

    /**
     * Method to set the hashmap
     * @param hashmap the hashmap
     */
    public void setHashmap(HashMap<String, String> hashmap){
        this.h = hashmap;
    }

    /**
     * Method to get the hashmap
     * @return the hashmap
     */
    public HashMap<String, String> getHashmap() {
        return h;
    }
}
