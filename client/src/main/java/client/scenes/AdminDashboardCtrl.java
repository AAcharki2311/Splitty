package client.scenes;

import client.scenes.MainCtrl;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apache.commons.lang3.RandomStringUtils;

import java.awt.*;

public class AdminDashboardCtrl {

    private MainCtrl mc;

    @Inject
    public AdminDashboardCtrl(MainCtrl mc) {
        this.mc = mc;
    }

    public void clickStart() {
        mc.showStart();
    }
}