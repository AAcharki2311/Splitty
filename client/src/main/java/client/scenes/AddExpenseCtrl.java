package client.scenes;

import client.utils.EventServerUtils;
import client.utils.ReadJSON;
import client.utils.LanguageSwitchInterface;
import commons.Expense;
import commons.Participant;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddExpenseCtrl implements Initializable, LanguageSwitchInterface {
    private final MainCtrl mc;
    private final ReadJSON jsonReader;
    @FXML
    private ImageView imageview;
    @FXML
    private Label titleOfScreen;
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
    private ComboBox<String> comboBoxNamePaid;
    private String[] names = {"John", "Chris", "Anna"};     //    here must go an array with names
    @FXML
    private ComboBox<String> comboBoxCurr;
    private String[] curNames = {"EUR", "USD", "CHF"};    //    here must go an array with currency names
    private final EventServerUtils server;
    private long eventid;
    @FXML
    private Label labelEventName;

    /**
     * Constructor of the AddExpenseCtrl
     * @param server represent the EventServerUtils
     * @param mc represent the MainCtrl
     * @param jsonReader is an instance of the ReadJSON class, so it can read JSONS
     */
    @Inject
    public AddExpenseCtrl(EventServerUtils server, MainCtrl mc, ReadJSON jsonReader) {
        this.mc = mc;
        this.jsonReader = jsonReader;
        this.server = server;
    }

    /**
     *
     * @param url represent the URL
     * @param resourceBundle represent the ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("images/logo-no-background.png");
        imageview.setImage(image);
        comboBoxNamePaid.getItems().addAll(names);
        comboBoxCurr.getItems().addAll(curNames);
    }

    /**
     * Method of the cancel button, when pressed, it shows the eventoverview screen
     */
    public void clickBack() {
        mc.showEventOverview(String.valueOf(eventid));
    }

    /**
     * Method of the OK button, when pressed, it checks the texfields and creates an entity and shows eventovervie screen
     */
    public void submitEdit() {
        try{
            var e = server.getEventByID(eventid);
            String title = titleTextField.getText();
            Double money = Double.parseDouble(moneyField.getText());
            LocalDate localDate = dateField.getValue();
            Date date = java.sql.Date.valueOf(localDate);
            String tag = "-";
            var p = new Participant();
//            ParticipantsServerUtil participantsServerUtil = new ParticipantsServerUtil();
//            var p = participantsServerUtil.getParticipantByID(1);
            if(validate(title, money, comboBoxNamePaid, comboBoxCurr, splitRBtn)){
                Expense exp = new Expense(e, p, money, date, title, tag);
                System.out.println("New Expense added: " +
                        exp.getTitle() + " " +
                        exp.getAmount() + " " +
                        exp.getDate());
                clickBack();
            } else {
                throw new Exception("Exception message");
            }
        } catch (Exception e){
            System.out.println("Something went wrong");
        }
    }

    /**
     * This method checks if the input is correct
     * @param title the title of the expense
     * @param money the amount of money
     * @param comboBoxNamePaid the name of the person who paid
     * @param comboBoxCurr the currency of the expense
     * @param splitRBtn the radio button that indicates if the expense is split
     * @return true if the input is correct, false if the input is incorrect
     */
    public boolean validate(String title, double money, ComboBox comboBoxNamePaid, ComboBox comboBoxCurr, RadioButton splitRBtn){
        if(title.isBlank() || money < 0 ||
                comboBoxNamePaid.getValue() == null ||
                comboBoxCurr.getValue() == null ||
                !splitRBtn.isSelected()){
            return false;
        }
        return true;
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language that the user wants to switch to
     */
    @Override
    public void langueageswitch(String taal) {
        String langfile = "language" + taal + ".json";
        HashMap<String, Object> h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        titleOfScreen.setText(h.get("key27").toString());
        fillInfoText.setText(h.get("key19").toString());
        whoPaidText.setText(h.get("key20").toString());
        titleText.setText(h.get("key21").toString());
        howMuchText.setText(h.get("key22").toString());
        whenText.setText(h.get("key23").toString());
        howToSplitText.setText(h.get("key24").toString());
        splitRBtn.setText(h.get("key25").toString());
        cancelBtn.setText(h.get("key26").toString());
    }

    /**
     * Updates the page with the right information
     * @param id the id of the event
     */
    public void update(String id){
        long eid = Long.parseLong(id);
        this.eventid = eid;
        System.out.println("reached");
        System.out.println(eid + " " + server.getEventByID(eid).getName());

        labelEventName.setText(server.getEventByID(eid).getName());

    }
}