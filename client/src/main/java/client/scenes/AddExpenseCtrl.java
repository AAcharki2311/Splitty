package client.scenes;

import client.utils.*;
import commons.Expense;
import commons.Participant;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class AddExpenseCtrl implements Initializable {
    /** INIT **/
    private final MainCtrl mc;
    private final ReadJSON jsonReader;
    private final EventServerUtils server;
    private final ParticipantsServerUtil partServer;
    private final ExpensesServerUtils expServer;
    private long eventid;
    private Participant selectedParticipant;
    private HashMap<String, String> h;
    /**
     * MENU
     **/
    @FXML
    private ImageView imgSet;
    @FXML
    private ImageView imgArrow;
    @FXML
    private ImageView imgHome;
    @FXML
    private ImageView imageviewFlag;
    /** PAGE FXML **/
    @FXML
    private ImageView imageview;
    @FXML
    private Text titleOfScreen;
    @FXML
    private Text fillInfoText;
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
    private Text labelEventName;
    @FXML
    private Text message;
    @FXML
    private Button cancelBtn;
    @FXML
    private RadioButton splitRBtn;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField moneyField;
    @FXML
    private TextField tagTextField;
    @FXML
    private DatePicker dateField;
    /** Combobox with Participant Info **/
    @FXML
    private ComboBox<String> comboBoxNamePaid;
    private ArrayList<String> names = new ArrayList<>();
    /** Combobox with Currency **/
    @FXML
    private ComboBox<String> comboBoxCurr;
    private String[] curNames = {"EUR", "USD", "CHF"};    //    here must go an array with currency names

    /**
     * Constructor of the AddExpenseCtrl
     * @param server represent the EventServerUtils
     * @param mc represent the MainCtrl
     * @param jsonReader is an instance of the ReadJSON class, so it can read JSONS
     * @param partServer represent the ParticipantsServerUtil
     * @param expServer represent the ExpensesServerUtils
     */
    @Inject
    public AddExpenseCtrl(EventServerUtils server, MainCtrl mc, ReadJSON jsonReader, ParticipantsServerUtil partServer, ExpensesServerUtils expServer) {
        this.mc = mc;
        this.jsonReader = jsonReader;
        this.server = server;
        this.partServer = partServer;
        this.expServer = expServer;
    }

    /**
     *
     * @param url represent the URL
     * @param resourceBundle represent the ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageview.setImage(new Image("images/logo-no-background.png"));
        imageviewFlag.setImage(new Image("images/br-circle-01.png"));
        imgSet.setImage(new Image("images/settings.png"));
        imgArrow.setImage(new Image("images/arrow.png"));
        imgHome.setImage(new Image("images/home.png"));

        comboBoxNamePaid.setOnAction(event -> {
            String nameParticipant = comboBoxNamePaid.getValue();
            if(nameParticipant == null){
                message.setText(h.get("key83"));
                return;
            }
            List<Participant> listAllParticipants = partServer.getAllParticipants()
                    .stream().filter(participant -> participant.getEvent().getId() == eventid).collect(Collectors.toList());
            selectedParticipant = listAllParticipants.stream().filter(participant -> participant.getName().equals(nameParticipant)).findAny().get();
        });
        comboBoxCurr.getItems().addAll(curNames);
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language that the user wants to switch to
     */
    public void langueageswitch(String taal) {
        String langfile = "language" + taal + ".json";
        h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        titleOfScreen.setText(h.get("key27"));
        fillInfoText.setText(h.get("key19"));
        whoPaidText.setText(h.get("key20"));
        titleText.setText(h.get("key21"));
        howMuchText.setText(h.get("key22"));
        whenText.setText(h.get("key23"));
        howToSplitText.setText(h.get("key24"));
        splitRBtn.setText(h.get("key25"));
        cancelBtn.setText(h.get("key26"));
        comboBoxNamePaid.setPromptText(h.get("key7"));
        Image imageFlag = new Image(h.get("key0"));
        imageviewFlag.setImage(imageFlag);
    }

    /**
     * Method of the OK button, when pressed, it checks the texfields and creates an entity and shows eventovervie screen
     */
    public void submit() {
        String errormessage = h.get("key84");
        try{
            if(comboBoxNamePaid.getValue() == null){
                errormessage = h.get("key83");
                throw new Exception();
            }

            var e = server.getEventByID(eventid);
            var p = selectedParticipant;

            if(moneyField.getText().isBlank() || dateField.getValue() == null){
                errormessage = h.get("key84");
                throw new Exception();
            }

            String title = titleTextField.getText();
            Double money = Double.parseDouble(moneyField.getText());
            Date date = java.sql.Date.valueOf(dateField.getValue());
            String tag = tagTextField.getText();
            String cur = comboBoxCurr.getValue();

            boolean duplicate = checkDuplicate(selectedParticipant.getName(), title);
            if(validate(title, money, date, comboBoxCurr, splitRBtn) && !duplicate){
                Expense exp = new Expense(e, p, money, date, title, tag, cur);
                expServer.addExpense(exp);
                mc.send("/app/events/"+eventid+"/expenses", exp);
                String message = h.get("key8") + ":\n" +
                        "_______________" + "\n" +
                        "Creditor: " + exp.getCreditor().getName() + "\n" +
                        h.get("key44") + ": " + exp.getTitle() + "\n" +
                        h.get("key45") + ": " + exp.getTag() + "\n" +
                        h.get("key42") + ": " + exp.getAmount() + " " + exp.getCur() + "\n" +
                        h.get("key41") + ": " + exp.getDate();
                JOptionPane.showMessageDialog(null, message);
                clickBack();
            } else {
                if(duplicate){
                    errormessage = h.get("key68");
                }else if(money < 0){
                    errormessage = h.get("key93");
                } else{
                    errormessage = h.get("key84");
                }
                throw new Exception();
            }
        } catch (Exception e){
            message.setText(errormessage);
        }
    }

    /**
     * This method checks if the name + title is a duplicate
     * @param name the name of the participant
     * @param title the title of the expense
     * @return true if it is a duplicate, false if it is not a duplicate
     */
    public boolean checkDuplicate(String name, String title){
        List<Expense> allExpenses = expServer.getExpenses().stream().filter(expense -> expense.getEvent().getId() == eventid).collect(Collectors.toList());
        List<String> namesOfAllParticipants = allExpenses.stream().map(Expense::getCreditor).map(Participant::getName).collect(Collectors.toList());
        List<String> titlesOfExpense = allExpenses.stream().map(Expense::getTitle).collect(Collectors.toList());
        return namesOfAllParticipants.contains(name) && titlesOfExpense.contains(title);
    }

    /**
     * This method checks if the input is correct
     * @param title the title of the expense
     * @param money the amount of money
     * @param date the date of expense
     * @param comboBoxCurr the currency of the expense
     * @param splitRBtn the radio button that indicates if the expense is split
     * @return true if the input is correct, false if the input is incorrect
     */
    public boolean validate(String title, double money, Date date, ComboBox comboBoxCurr, RadioButton splitRBtn){
        return !title.isBlank() && !(money < 0) && date != null && comboBoxCurr.getValue() != null &&
                splitRBtn.isSelected();
    }

    /**
     * Updates the page with the right information
     * @param id the id of the event
     */
    public void update(String id){
        long eid = Long.parseLong(id);
        this.eventid = eid;
        labelEventName.setText(server.getEventByID(eid).getName());

        List<Participant> listAllParticipants = partServer.getAllParticipants()
                .stream().filter(participant -> participant.getEvent().getId() == eventid).collect(Collectors.toList());
        names = (ArrayList<String>) listAllParticipants.stream().map(Participant::getName).collect(Collectors.toList());
        comboBoxNamePaid.getItems().clear();
        comboBoxNamePaid.getItems().addAll(names);
    }

    /**
     * Method of the cancel button, when pressed, it shows the eventoverview screen
     */
    public void clickBack() {
        mc.showEventOverview(String.valueOf(eventid));
    }

    /**
     * Method of the settings button, when pressed, it shows the keyboard combo's
     */
    public void clickSettings() {
        mc.help();
    }

    /**
     * Used to get back to the Start Screen
     * @throws IOException if something went wrong with showing the start screen
     */
    public void clickHome() throws IOException {
        mc.showStart();
    }
}
