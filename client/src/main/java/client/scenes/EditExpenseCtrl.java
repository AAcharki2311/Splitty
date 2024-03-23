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

import javax.swing.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EditExpenseCtrl implements Initializable, LanguageSwitchInterface {
    private final MainCtrl mc;
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
    private Button deleteBtn;
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
    private final EventServerUtils server;
    private long eventid;
    @FXML
    private Label labelEventName;

    /**
     * Constructor of the EditExpenseCtrl
     * @param server represent the EventServerUtils
     * @param mc represent the MainCtrl
     * @param jsonReader is an instance of the ReadJSON class, so it can read JSONS
     */
    @Inject
    public EditExpenseCtrl(EventServerUtils server, MainCtrl mc, ReadJSON jsonReader) {
        this.mc = mc;
        this.jsonReader = jsonReader;
        this.server = server;
    }

    /**
     * This method sets the image for the imageview and adds the items to the comboboxes
     * @param url represent the URL
     * @param resourceBundle represent the ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("images/logo-no-background.png");
        imageview.setImage(image);
        comboBoxName.getItems().addAll(names);
        comboBoxExpensesTitle.getItems().addAll(namesOfExpenses);
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
     * Method of the OK button, when pressed, it checks the texfields and creates an entity and shows eventoverview screen
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
            if(validate(title, money, comboBoxNamePaid, comboBoxCurr, comboBoxName, comboBoxExpensesTitle, splitRBtn)){
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
     * @param comboBoxName the name of the expense
     * @param comboBoxExpensesTitle the title of the expense
     * @param splitRBtn the radio button that indicates if the expense is split
     * @return true if the input is correct, false if the input is incorrect
     */
    public boolean validate(String title, double money, ComboBox comboBoxNamePaid, ComboBox comboBoxCurr, ComboBox comboBoxName, ComboBox comboBoxExpensesTitle, RadioButton splitRBtn){
        if(title.isBlank() || money < 0 ||
                comboBoxNamePaid.getValue() == null ||
                comboBoxCurr.getValue() == null ||
                comboBoxName.getValue() == null ||
                comboBoxExpensesTitle.getValue() == null ||
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
        deleteBtn.setText(h.get("key39").toString());

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

    /**
     * Method of the delete button, when pressed, it deletes the expense
     */
    public void delete(){
        System.out.println("Delete button pressed");
        String name = null;
        if(name == null){
            System.out.println("No expense selected");
            return;
        }
        int choice = JOptionPane.showOptionDialog(null,"Are you sure you want to delete?", "Delete Confirmation",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
                new String[]{"DELETE", "GO BACK"}, "default");

        if(choice == JOptionPane.OK_OPTION){
//            Optional<Expense> e = partServer.getAllExpense()
//                    .stream().filter(expense -> expense.getName().equals(name)).findAny();
//            partServer.deleteExpenseByID(e.get().getId());
            JOptionPane.showMessageDialog(null, "Expense deleted");
            update(String.valueOf(eventid));
        } else{
            System.out.println("Operation cancelled by the user. Expense remains unchanged.");
        }
    }
}