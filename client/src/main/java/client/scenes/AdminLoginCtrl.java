package client.scenes;

import client.utils.EventServerUtils;
import com.google.inject.Inject;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.apache.commons.lang3.RandomStringUtils;
import java.net.URL;
import java.util.*;

public class AdminLoginCtrl implements Initializable {

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
    @FXML
    private ImageView imageviewFlag;
    /** PAGE **/
    @FXML
    private ImageView imgMessage;
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
        imageview.setImage(new Image("images/logo-no-background.png"));
        imageviewFlag.setImage(new Image("images/br-circle-01.png"));
        imgSet.setImage(new Image("images/settings.png"));
        imgArrow.setImage(new Image("images/arrow.png"));
        imgHome.setImage(new Image("images/home.png"));
    }

    /**
     * Constructer for the AdminLogin Controller
     * @param mc the main controller
     * @param server the server
     */
    @Inject
    public AdminLoginCtrl(EventServerUtils server, MainCtrl mc) {
        this.mc = mc;
        this.pw = RandomStringUtils.random(8, true, true);
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
            imgMessage.setImage(new Image("images/notifications/Slide1.png"));
            PauseTransition pause = new PauseTransition(Duration.seconds(6));
            pause.setOnFinished(p -> imgMessage.setImage(null));
            pause.play();
            return;
        }
    }
}
