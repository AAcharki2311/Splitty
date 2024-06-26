package client.scenes;

import client.utils.*;
import commons.Expense;
import commons.Participant;
import jakarta.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class EditExpenseCtrl implements Initializable {
    /** INIT **/
    private final MainCtrl mc;
    private final ReadJSON jsonReader;
    private final EventServerUtils server;
    private final ParticipantsServerUtil partServer;
    private final ExpensesServerUtils expServer;
    private long eventid;
    private Expense selectedExpense;
    private HashMap<String, String> h;
    private HashMap<String, String> ht;
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
    private Text changeTheExpenseOfText;
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
    private Button addTagBtn;
    @FXML
    private Button editTagBtn;
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
    private ArrayList<Expense> changedExpenses = new ArrayList<>();
    /** Combobox with Tags **/
    @FXML
    private ComboBox<String> comboBoxTag;
    private ArrayList<String> eventTags = new ArrayList<>();

    /**
     * Constructor of the EditExpenseCtrl
     * @param server represent the EventServerUtils
     * @param mc represent the MainCtrl
     * @param jsonReader is an instance of the ReadJSON class, so it can read JSONS
     * @param partServer represent the ParticipantsServerUtil
     * @param expServer represent the ExpensesServerUtils
     */
    @Inject
    public EditExpenseCtrl(EventServerUtils server, MainCtrl mc, ReadJSON jsonReader, ParticipantsServerUtil partServer,
                           ExpensesServerUtils expServer) {
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
        imageview.setImage(new Image("images/logo-no-background.png"));
        imageviewFlag.setImage(new Image("images/br-circle-01.png"));
        imgSet.setImage(new Image("images/settings.png"));
        imgArrow.setImage(new Image("images/arrow.png"));
        imgHome.setImage(new Image("images/home.png"));

        comboBoxCurr.getItems().addAll(curNames);

        comboBoxTag.getItems().clear();
        comboBoxTag.getItems().addAll(eventTags);

        comboBoxName.setOnAction(event -> {
            String nameParticipant = comboBoxName.getValue();

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

            selectedExpense = expServer.getExpenses().stream()
                    .filter(expense -> expense.getCreditor().getName().equals(nameParticipant)
                            && expense.getTitle().equals(title))
                    .findAny().get();

            comboBoxNamePaid.setValue(selectedExpense.getCreditor().getName());
            titleTextField.setText(selectedExpense.getTitle());
            moneyField.setText(String.valueOf(selectedExpense.getAmount()));
            comboBoxCurr.setValue(selectedExpense.getCur());
            Date date = selectedExpense.getDate();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dateField.setValue(localDate);
            splitRBtn.setSelected(true);
            comboBoxTag.setValue(selectedExpense.getTag());
            comboBoxCurr.setValue(selectedExpense.getCur());
        });
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language that the user wants to switch to
     */
    public void langueageswitch(String taal) {
        String langfile = "language" + taal + ".json";
        h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        titleOfScreen.setText(h.get("key16"));
        changeTheExpenseOfText.setText(h.get("key17"));
        fillInfoText.setText(h.get("key19"));
        whoPaidText.setText(h.get("key20"));
        titleText.setText(h.get("key21"));
        howMuchText.setText(h.get("key22"));
        whenText.setText(h.get("key23"));
        howToSplitText.setText(h.get("key24"));
        splitRBtn.setText(h.get("key25"));
        cancelBtn.setText(h.get("key26"));
        deleteBtn.setText(h.get("key39"));
        comboBoxNamePaid.setPromptText(h.get("key7"));
        comboBoxName.setPromptText(h.get("key7"));
        comboBoxExpensesTitle.setPromptText(h.get("key8"));
        addTagBtn.setText(h.get("key10"));
        editTagBtn.setText(h.get("key9"));
        Image imageFlag = new Image(h.get("key0"));
        imageviewFlag.setImage(imageFlag);
    }

    /**
     * Method of the OK button, when pressed, it checks the texfields and creates an entity and shows eventoverview screen
     */
    public void submitEdit() {
        String partName = comboBoxName.getValue();
        String expTitle = comboBoxExpensesTitle.getValue();
        String changePartName = comboBoxNamePaid.getValue();

        if(partName == null || changePartName == null){
            // message.setText(h.get("key83"));
            JOptionPane.showOptionDialog(null, h.get("key83"),h.get("key114"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{}, null);

            return;
        }
        if(expTitle == null){
            JOptionPane.showOptionDialog(null, h.get("key92"),h.get("key114"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{}, null);
            //message.setText(h.get("key92"));
            return;
        }

        try{
            var e = server.getEventByID(eventid);

            List<Participant> listAllParticipants = partServer.getAllParticipants()
                    .stream().filter(participant -> participant.getEvent().getId() == eventid)
                    .collect(Collectors.toList());

            var p = listAllParticipants.stream().filter(participant -> participant.getName().equals(changePartName)).findAny().get();

            String title = titleTextField.getText();
            double money = Double.parseDouble(moneyField.getText());
            Date date = java.sql.Date.valueOf(dateField.getValue());
            String tag = comboBoxTag.getValue();
            String cur = comboBoxCurr.getValue();

            boolean duplicate = checkDuplicate(changePartName, title);
            if(validate(title, money, date, comboBoxCurr, splitRBtn) && !duplicate){
                Expense exp = new Expense(e, p, money, date, title, tag, cur);
                int choice = JOptionPane.showOptionDialog(null,h.get("key85"), h.get("key86"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        new String[]{"Update", h.get("key26")}, "default");

                if(choice == JOptionPane.OK_OPTION){
                    Expense newExpense;
                    changedExpenses.add(selectedExpense);
                    exp.setId(selectedExpense.getId());
                    if(!(selectedExpense.getCreditor().equals(exp.getCreditor()))){
                        expServer.deleteExpenseByID(selectedExpense.getId());
                        newExpense = expServer.addExpense(exp);
                    }else{
                        newExpense = expServer.updateExpenseByID(selectedExpense.getId(), exp);
                    }
                    mc.send("/app/events/"+eventid+"/expenses", newExpense);
                    String message = h.get("key138") + ":\n" +
                            "_______________" + "\n" +
                            h.get("key137") + ": " + exp.getCreditor().getName() + "\n" +
                            h.get("key44") + ": " + exp.getTitle() + "\n" +
                            h.get("key45") + ": " + exp.getTag() + "\n" +
                            h.get("key42") + ": " + exp.getAmount() + " " + exp.getCur() + "\n" +
                            h.get("key41") + ": " + exp.getDate();
                    JOptionPane.showMessageDialog(null, message);
                    clickBack();
                }
            } else {
                JOptionPane.showOptionDialog(null , elsemethod(money, h),h.get("key114"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{}, null);
                // message.setText(elsemethod(money, h));
            }
        } catch (Exception e){
            JOptionPane.showOptionDialog(null, h.get("key84"),h.get("key114"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{}, null);
            // message.setText(h.get("key84"));
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
        allExpenses.remove(selectedExpense);
        List<String> namesOfAllParticipants = allExpenses.stream().map(Expense::getCreditor).map(Participant::getName).collect(Collectors.toList());
        List<String> titlesOfExpense = allExpenses.stream().map(Expense::getTitle).collect(Collectors.toList());
        return namesOfAllParticipants.contains(name) && titlesOfExpense.contains(title);
    }

    /**
     * This method shows a message if the input is incorrect
     * @param money the amount of money
     * @param h the hashmap with the messages
     * @return the message
     */
    public String elsemethod(double money, HashMap<String, String> h){
        if(money < 0){
            return h.get("key93");
        } else {
            return h.get("key84");
        }
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
        return !title.isBlank() && !(money < 0) && date != null && comboBoxCurr != null &&
                comboBoxCurr.getValue() != null && splitRBtn != null &&
                splitRBtn.isSelected();
    }

    /**
     * Method of the delete button, when pressed, it deletes the expense
     */
    public void delete(){
        String partName = comboBoxName.getValue();
        String expTitle = comboBoxExpensesTitle.getValue();
        if(partName == null){
            JOptionPane.showOptionDialog(null, h.get("key83"),h.get("key114"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{}, null);
            // message.setText(h.get("key83"));
            return;
        }
        if(expTitle == null){
            JOptionPane.showOptionDialog(null, h.get("key92"),h.get("key114"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{}, null);
            // message.setText(h.get("key92"));
            return;
        }

        int choice = JOptionPane.showOptionDialog(null,h.get("key82"), h.get("key66"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
                new String[]{h.get("key67"), h.get("key26")}, "default");

        if(choice == JOptionPane.OK_OPTION){
            changedExpenses.add(selectedExpense);
            expServer.deleteExpenseByID(selectedExpense.getId());
            JOptionPane.showMessageDialog(null, h.get("key91"));
            update(String.valueOf(eventid));
            setEverythingEmpty();
        }
    }

    /**
     * This method sets all fields to empty
     */
    public void setEverythingEmpty(){
        comboBoxName.setValue(null);
        comboBoxExpensesTitle.setValue(null);
        comboBoxNamePaid.setValue(null);
        titleTextField.setText(null);
        moneyField.setText(null);
        dateField.setValue(null);
        splitRBtn.setSelected(false);
    }

    /**
     * This method sets all fields to the information of the expense
     * @param expense the expense
     */
    public void setComboBoxExpenses(Expense expense){
        comboBoxName.setValue(expense.getCreditor().getName());
        comboBoxExpensesTitle.setValue(expense.getTitle());
        comboBoxNamePaid.setValue(expense.getCreditor().getName());
        titleTextField.setText(expense.getTitle());
        moneyField.setText(String.valueOf(expense.getAmount()));
        dateField.setValue(expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        splitRBtn.setSelected(true);
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
        comboBoxTag.getItems().clear();
        eventTags.clear();
        addTags();
    }

    /**
     * Add tags to comboBox
     */
    public void addTags() {
        ht = jsonReader.readJsonToMap("src/main/resources/tagcolors.json");
        // Get all keys from the JSON object
        for (String key : ht.keySet()) {
            if (key.endsWith("?" + String.valueOf(eventid))) {
                String extractedString = key.substring(0, key.indexOf("?"));
                eventTags.add(extractedString);
            }
        }
        comboBoxTag.getItems().addAll(eventTags);
    }

    /**
     * Method of the see button, when pressed, it shows the changed expenses
     */
    public void showChangedExpenses(){
        changedExpenses = (ArrayList<Expense>) changedExpenses.stream().filter(e -> e.getEvent().getId() == eventid).collect(Collectors.toList());
        if(changedExpenses.isEmpty()){
            JOptionPane.showMessageDialog(null, h.get("key90"));
        } else {
            TableView<Expense> tableView = setupTable();

            Stage newStage = new Stage();
            newStage.setTitle(h.get("key89"));
            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().addAll(tableView);
            Scene secondscene = new Scene(secondaryLayout, 500, 500);
            newStage.setScene(secondscene);
            newStage.show();

            tableView.setOnMouseClicked(event -> {
                try{
                    Expense selectedItem = tableView.getSelectionModel().getSelectedItem();
                    if(selectedItem.getTitle() == null){return;}

                    int choice = JOptionPane.showOptionDialog(null,h.get("key87"), h.get("key88"),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
                            new String[]{"Reset", h.get("key26")}, "default");

                    if(choice == JOptionPane.OK_OPTION) {
                        long id = selectedItem.getId();
                        try{
                            changedExpenses.add(expServer.getExpenseByID(id));
                            expServer.updateExpenseByID(id, selectedItem);
                        } catch (Exception e){
                            expServer.addExpense(selectedItem);
                        }
                        changedExpenses.remove(selectedItem);
                        JOptionPane.showMessageDialog(null, (h.get("key8") + " reset"));
                        newStage.close();
                        clickBack();
                    }
                } catch (NullPointerException e){
                    return;
                }
            });
        }
    }

    /**
     * This method sets up the table with the changed expenses
     * @return the tableview with the changed expenses
     */
    public TableView<Expense> setupTable() {
        ObservableList<Expense> data = FXCollections.observableArrayList(changedExpenses);
        TableView<Expense> tableView = new TableView<>();
        tableView.setItems(data);

        TableColumn<Expense, String> colDate = new TableColumn<>(h.get("key41"));
        TableColumn<Expense, String> colAm = new TableColumn<>(h.get("key42"));
        TableColumn<Expense, String> colPart = new TableColumn<>(h.get("key43"));
        TableColumn<Expense, String> colTitle = new TableColumn<>(h.get("key44"));

        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        colDate.setCellValueFactory(q -> new SimpleStringProperty(formatter.format(q.getValue().getDate())));
        colAm.setCellValueFactory(q -> new SimpleStringProperty(String.valueOf(q.getValue().getAmount())));
        colPart.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getCreditor().getName()));
        colTitle.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getTitle()));

        tableView.getColumns().addAll(colDate, colAm, colPart, colTitle);
        return tableView;
    }

    /**
     * Method of the cancel button, when pressed, it shows the eventoverview screen
     */
    public void clickBack() {
        mc.showEventOverview(String.valueOf(eventid));
    }

    /**
     * This method sets the changed expenses
     * @return the changed expenses
     */
    public ArrayList<Expense> getChangedExpenses() {
        return changedExpenses;
    }

    /**
     * This method sets the changed expenses
     * @param changedExpenses the changed expenses
     */
    public void setChangedExpenses(ArrayList<Expense> changedExpenses) {
        this.changedExpenses = changedExpenses;
    }

    /**
     * Method of the settings button, when pressed, it shows the keyboard combo's
     */
    public void clickSettings() {
        mc.help(h);
    }

    /**
     * Used to get back to the Start Screen
     * @throws IOException if something went wrong with showing the start screen
     */
    public void clickHome() throws IOException {
        mc.showStart();
    }

    /**
     * Add tag to this event
     */
    public void clickAddTag(){
        while(true){
            JTextField textFieldName = new JTextField();
            JTextField textFieldColor = new JTextField();
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel(h.get("key123")));
            panel.add(textFieldName);
            panel.add(new JLabel(h.get("key124")));
            panel.add(textFieldColor);
            Object[] options = {h.get("key10"), h.get("key99")};
            int result = JOptionPane.showOptionDialog(null, panel, h.get("key63"),
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (result == JOptionPane.OK_OPTION) {
                String name = textFieldName.getText();
                String color = textFieldColor.getText();
                if(name.isBlank() || color.isBlank()){
                    JOptionPane.showMessageDialog(null, h.get("key64"));
                } else{
                    // do something
                    HashMap<String, String> ht = jsonReader.readJsonToMap("src/main/resources/tagcolors.json");
                    ht.put(name+"?"+eventid, color);
                    jsonReader.writeMapToJsonFile(ht, "src/main/resources/tagcolors.json");
                    comboBoxTag.getItems().clear();
                    eventTags.clear();
                    addTags();
                    comboBoxTag.setValue(name);
                    break;
                }
            } else {
                break;
            }
        }
    }

    /**
     * Edit tag of this event
     */
    public void clickEditTag() {
        JTextField textFieldName = new JTextField();
        JTextField textFieldColor = new JTextField();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(h.get("key123")));
        panel.add(textFieldName);
        textFieldName.setText(comboBoxTag.getValue());
        panel.add(new JLabel(h.get("key124")));
        panel.add(textFieldColor);

        ht = jsonReader.readJsonToMap("src/main/resources/tagcolors.json");
        textFieldColor.setText(ht.get(comboBoxTag.getValue()+"?"+eventid));

        Object[] options = {h.get("key10"), h.get("key67")};
        int result = JOptionPane.showOptionDialog(null, panel, h.get("key63"),
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (result == JOptionPane.OK_OPTION) {
            String name = textFieldName.getText();
            String color = textFieldColor.getText();
            if(name.isBlank() || color.isBlank()){
                JOptionPane.showMessageDialog(null, h.get("key64"));
            } else{
                ht.remove(comboBoxTag.getValue()+"?"+eventid);
                ht.put(name+"?"+eventid, color);
                jsonReader.writeMapToJsonFile(ht, "src/main/resources/tagcolors.json");
                comboBoxTag.getItems().clear();
                eventTags.clear();
                addTags();
                comboBoxTag.setValue(name);
            }
        } else if(result == JOptionPane.NO_OPTION){
            ht.remove(comboBoxTag.getValue()+"?"+eventid);
            jsonReader.writeMapToJsonFile(ht, "src/main/resources/tagcolors.json");
            comboBoxTag.getItems().clear();
            eventTags.clear();
            addTags();
        }
    }
}