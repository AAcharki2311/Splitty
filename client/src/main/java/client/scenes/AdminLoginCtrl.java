package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import org.apache.commons.lang3.RandomStringUtils;

public class AdminLoginCtrl {

    @FXML
    private PasswordField inputpw;

    @FXML
    private Label pwtext;

    private final MainCtrl mc;
    private final String pw;

    /**
     * Constructer for the AdminLogin Controller
     * @param m the main controller
     */
    @Inject
    public AdminLoginCtrl(MainCtrl m) {
        this.mc = m;
        this.pw = RandomStringUtils.random(8, true, true);
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
}
