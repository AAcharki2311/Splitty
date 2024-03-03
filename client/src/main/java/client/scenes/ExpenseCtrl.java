package client.scenes;

import jakarta.inject.Inject;
//import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ExpenseCtrl implements Initializable {

    private MainCtrl mc;

    @FXML
    private ComboBox<String> comboBoxOne;
    @FXML
    private ComboBox<String> comboBoxTwo;
    @FXML
    private TextField TextFieldMoney;

    //    here must go an array with names
    private String[] names = {"John", "Chris", "Anna"};
    //    here must go an array with currency names
    private String[] curNames = {"EUR", "USD", "CHF"};

    @Inject
    public ExpenseCtrl(MainCtrl mc) {
        this.mc = mc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxOne.getItems().addAll(names);
        comboBoxTwo.getItems().addAll(curNames);
    }

    public void check() {
//        TextFieldMoney.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("\\d*\\.?\\d*")) { // Regex to allow digits and optional decimal point
//                TextFieldMoney.setText(oldValue); // Revert to the old value if new value doesn't match
//            }
//        });
    }

    public void clickBack() {
        mc.showEventOverview();
    }


}