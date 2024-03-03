package client.scenes;

// import client.scenes.MainCtrl;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;

// import java.awt.*;
import java.util.ResourceBundle;

public class EventOverviewCtrl implements Initializable {

    private MainCtrl mc;

    @FXML
    private ChoiceBox<String> choiceBoxOne;

//    here must go an array with names
    private String[] names = {"John", "Chris", "Anna"};

    @Inject
    public EventOverviewCtrl(MainCtrl mc) {
        this.mc = mc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxOne.getItems().addAll(names);
    }

    public void clickInvite() {
        mc.showInvite();
    }

    public void clickBack() {
        mc.showStart();
    }


}