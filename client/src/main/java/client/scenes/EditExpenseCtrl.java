package client.scenes;

import client.utils.ReadJSON;
import client.utils.languageSwitchInterface;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EditExpenseCtrl implements Initializable, languageSwitchInterface {

    private MainCtrl mc;
    private final ReadJSON jsonReader;
    @FXML
    private ImageView imageview;
    @FXML
    private Label titleOfScreen;
    @FXML
    private Label changeTheExpenseOfText;
    @FXML
    private Label calledText;
    @FXML
    private Label fillInfoText;
    @FXML
    private Label whoPaidText;
    @FXML
    private Label titleText;
    @FXML
    private Label howMuchText;
    @FXML
    private Label whenText;
    @FXML
    private Label howToSplitText;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField moneyField;
    @FXML
    private DatePicker dateField;
    @FXML
    private RadioButton splitRBtn;
    @FXML
    private ComboBox<String> comboBoxName;
    private String[] names = {"John", "Chris", "Anna"};     //    here must go an array with names
    @FXML
    private ComboBox<String> comboBoxExpensesTitle;
    private String[] namesOfExpenses = {"Expense1", "Expense2", "Expense3"};    //    here must go an array with names of expenses
    @FXML
    private ComboBox<String> comboBoxNamePaid;
    @FXML
    private ComboBox<String> comboBoxCurr;
    private String[] curNames = {"EUR", "USD", "CHF"};    //    here must go an array with currency names

    @Inject
    public EditExpenseCtrl(MainCtrl mc, ReadJSON jsonReader) {
        this.mc = mc;
        this.jsonReader = jsonReader;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("images/logo-no-background.png");
        imageview.setImage(image);
        comboBoxName.getItems().addAll(names);
        comboBoxExpensesTitle.getItems().addAll(namesOfExpenses);
        comboBoxNamePaid.getItems().addAll(names);
        comboBoxCurr.getItems().addAll(curNames);
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

    public void submitEdit() {
        // check each textfield and combobox if they are filled in
        // if not, give a warning message and don't submit the form: please fill all in
        // create a new expense object and add it to the list of expenses
        mc.showEventOverview();
    }


    @Override
    public void langueageswitch(String taal) {
        HashMap<String, Object> h = jsonReader.readJsonToMap("C:\\Users\\ayoub\\oopp-ayoubacharki\\TEAM\\oopp-team-23\\client\\src\\main\\resources\\languageJSONS\\language" + taal + ".json");
        titleOfScreen.setText(h.get("key16").toString());
        changeTheExpenseOfText.setText(h.get("key17").toString());
        calledText.setText(h.get("key18").toString());
        fillInfoText.setText(h.get("key19").toString());
        whoPaidText.setText(h.get("key20").toString());
        titleText.setText(h.get("key21").toString());
        howMuchText.setText(h.get("key22").toString());
        whenText.setText(h.get("key23").toString());
        howToSplitText.setText(h.get("key24").toString());
        splitRBtn.setText(h.get("key25").toString());
        cancelBtn.setText(h.get("key26").toString());
    }
}