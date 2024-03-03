package client.scenes;

import client.scenes.MainCtrl;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apache.commons.lang3.RandomStringUtils;

import java.awt.*;

public class StartScreenCtrl {

    private MainCtrl mc;
    @FXML
    private Button createBtn;

    @Inject
    public StartScreenCtrl(MainCtrl mc) {
        this.mc = mc;
    }

    public void clickEvent() {
        mc.showEventOverview();
    }

    public void clickAdmin() {
        mc.showAdminLogin();
    }
}