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

    private MainCtrl mc;
    private String pw;
    @Inject
    public AdminLoginCtrl(MainCtrl m) {
        this.mc = m;
        this.pw = RandomStringUtils.random(8, true, true);
        System.out.println("Your random password is: " + pw);
    }
    public void clickStart () {
        mc.showStart();
    }

    public void clickLogin() {
        String input = inputpw.getText();
        if (input.equals(pw)){
            mc.showAdminDashboard();
        }
        else {
            pwtext.setText("Password is incorrect, please try again");
        }
    }
}