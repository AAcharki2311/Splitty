package client.scenes;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;

import java.util.ResourceBundle;

public class EventOverviewCtrl implements Initializable {

    private MainCtrl mc;

    @FXML
    private ComboBox<String> comboBoxOne;

    //    here must go an array with names
    private String[] names = {"John", "Chris", "Anna"};

    @Inject
    public EventOverviewCtrl(MainCtrl mc) {
        this.mc = mc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxOne.getItems().addAll(names);
    }

    public void clickInvite() {
        mc.showInvite();
    }

    public void clickBack() {
        mc.showStart();
    }

    public void clickAddExpense() {
        mc.showExpense();
    }

    public void clickAddParticipant() {
        mc.showParticipant();
    }

    public void clickEditParticipant() {
        mc.showEditParticipant();
    }

    public void clickEditExpense() {
        mc.showEditExpense();
    }



}