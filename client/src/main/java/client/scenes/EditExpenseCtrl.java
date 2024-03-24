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
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class EditExpenseCtrl implements Initializable, LanguageSwitchInterface {
    /** INIT **/
    private final MainCtrl mc;
    private final ReadJSON jsonReader;
    private final EventServerUtils server;
    private final ParticipantsServerUtil partServer;
    private final ExpensesServerUtils expServer;
    private long eventid;
    private Expense selectedExpense;
    /** PAGE FXML **/
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
    private Label labelEventName;
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
    private Text message;
    /** Combobox with Participant Info **/
    @FXML
    private ComboBox<String> comboBoxName;
    @FXML
    private ComboBox<String> comboBoxNamePaid;
    private ArrayList<String> names = new ArrayList<>();
    /** Combobox with Expense title **/
    @FXML
    private ComboBox<String> comboBoxExpensesTitle;
    private ArrayList<String> namesOfExpenses = new ArrayList<>();
    /** Combobox with Currency **/
    @FXML
    private ComboBox<String> comboBoxCurr;
    private String[] curNames = {"EUR", "USD", "CHF"};    //    here must go an array with currency names

    /**
     * Constructor of the EditExpenseCtrl
     * @param server represent the EventServerUtils
     * @param mc represent the MainCtrl
     * @param jsonReader is an instance of the ReadJSON class, so it can read JSONS
     * @param partServer represent the ParticipantsServerUtil
     * @param expServer represent the ExpensesServerUtils
     */
    @Inject
    public EditExpenseCtrl(EventServerUtils server, MainCtrl mc, ReadJSON jsonReader, ParticipantsServerUtil partServer, ExpensesServerUtils expServer) {
        this.mc = mc;
        this.jsonReader = jsonReader;
        this.server = server;
        this.partServer = partServer;
        this.expServer = expServer;
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

        comboBoxCurr.getItems().addAll(curNames);

        comboBoxName.setOnAction(event -> {
            String nameParticipant = comboBoxName.getValue();
            if(nameParticipant == null){
                message.setText("No participant selected");
                return;
            }

            List<Expense> listAllExpense = expServer.getExpenses()
                    .stream().filter(expense -> expense.getEvent().getId() == eventid).collect(Collectors.toList());

            List<Expense> listExpenseOfParticipant = listAllExpense.stream()
                    .filter(expense -> expense.getCreditor().getName().equals(nameParticipant))
                    .collect(Collectors.toList());

            namesOfExpenses = (ArrayList<String>) listExpenseOfParticipant.stream().map(Expense::getTitle).collect(Collectors.toList());
            comboBoxExpensesTitle.getItems().clear();
            comboBoxExpensesTitle.getItems().addAll(namesOfExpenses);
        });

        comboBoxExpensesTitle.setOnAction(event -> {
            String nameParticipant = comboBoxName.getValue();
            String title = comboBoxExpensesTitle.getValue();
            if(title == null){
                message.setText("No expense selected");
                return;
            }

            selectedExpense = expServer.getExpenses().stream()
                    .filter(expense -> expense.getCreditor().getName().equals(nameParticipant)
                            && expense.getTitle().equals(title))
                    .findAny().get();

            comboBoxNamePaid.setValue(selectedExpense.getCreditor().getName());
            titleTextField.setText(selectedExpense.getTitle());
            moneyField.setText(String.valueOf(selectedExpense.getAmount()));
            Date date = selectedExpense.getDate();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dateField.setValue(localDate);
            splitRBtn.setSelected(true);
        });
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
     * Method of the OK button, when pressed, it checks the texfields and creates an entity and shows eventoverview screen
     */
    public void submitEdit() {
        String partName = comboBoxName.getValue();
        String expTitle = comboBoxExpensesTitle.getValue();
        String changePartName = comboBoxNamePaid.getValue();
        if(partName == null || changePartName == null){
            message.setText("No participant selected");
            return;
        }
        if(expTitle == null){
            message.setText("No expense selected");
            return;
        }

        try{
            var e = server.getEventByID(eventid);

            List<Participant> listAllParticipants = partServer.getAllParticipants()
                    .stream().filter(participant -> participant.getEvent().getId() == eventid)
                    .collect(Collectors.toList());

            var p = listAllParticipants.stream().filter(participant -> participant.getName().equals(changePartName)).findAny().get();

            String title = titleTextField.getText();
            Double money = Double.parseDouble(moneyField.getText());
            Date date = java.sql.Date.valueOf(dateField.getValue());
            String tag = "none";

            if(validate(title, money, comboBoxCurr, splitRBtn)){
                Expense exp = new Expense(e, p, money, date, title, tag);

                int choice = JOptionPane.showOptionDialog(null,"Are you sure you want to update?", "Update Confirmation",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        new String[]{"Update", "Cancel"}, "default");

                if(choice == JOptionPane.OK_OPTION){
//                    expServer.updateExpenseByID(selectedExpense.getId(),exp);
                    JOptionPane.showMessageDialog(null, "Expense Updated");
                    clickBack();
                }
            } else {
                message.setText("Please fill in all fields correctly");
            }
        } catch (Exception e){
            message.setText("Please fill in all fields correctly");
        }
    }

    /**
     * This method checks if the input is correct
     * @param title the title of the expense
     * @param money the amount of money
     * @param comboBoxCurr the currency of the expense
     * @param splitRBtn the radio button that indicates if the expense is split
     * @return true if the input is correct, false if the input is incorrect
     */
    public boolean validate(String title, double money, ComboBox comboBoxCurr, RadioButton splitRBtn){
        return !title.isBlank() && !(money < 0) &&
                comboBoxCurr.getValue() != null &&
                splitRBtn.isSelected();
    }

    /**
     * Method of the delete button, when pressed, it deletes the expense
     */
    public void delete(){
        String partName = comboBoxName.getValue();
        String expTitle = comboBoxExpensesTitle.getValue();
        if(partName == null){
            message.setText("No participant selected");
            return;
        }
        if(expTitle == null){
            message.setText("No expense selected");
            return;
        }

        int choice = JOptionPane.showOptionDialog(null,"Are you sure you want to delete?", "Delete Confirmation",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
                new String[]{"DELETE", "GO BACK"}, "default");

        if(choice == JOptionPane.OK_OPTION){
            expServer.deleteExpenseByID(selectedExpense.getId());
            JOptionPane.showMessageDialog(null, "Expense deleted");
            update(String.valueOf(eventid));
        }
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
        names.clear();
        for (Participant p : listAllParticipants) {names.add(p.getName());}
        comboBoxName.getItems().clear();
        comboBoxName.getItems().addAll(names);
        comboBoxNamePaid.getItems().clear();
        comboBoxNamePaid.getItems().addAll(names);
        comboBoxExpensesTitle.setValue(null);
    }

    /**
     * Method of the cancel button, when pressed, it shows the eventoverview screen
     */
    public void clickBack() {
        mc.showEventOverview(String.valueOf(eventid));
    }
}