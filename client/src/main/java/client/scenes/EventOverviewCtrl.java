package client.scenes;

import client.utils.*;
import commons.Event;
import commons.Expense;
import commons.Participant;
import jakarta.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class EventOverviewCtrl implements Initializable, LanguageSwitchInterface {
    /** BASIS **/
    private final MainCtrl mc;
    private final ReadJSON jsonReader;
    private final EventServerUtils server;
    private final ParticipantsServerUtil partServer;
    private final ExpensesServerUtils expServer;

    /** MENU **/
    @FXML
    private ImageView imgSet;
    @FXML
    private ImageView imgArrow;
    @FXML
    private ImageView imgHome;
    @FXML
    private ImageView imageviewFlag;
    @FXML
    private ComboBox comboboxLanguage;
    /** PAGE **/
    private long eventid;
    private List<String> languages = new ArrayList<>(Arrays.asList("Dutch", "English", "French"));
    @FXML
    private Text partictext;
    @FXML
    private Text expenstext;
    @FXML
    private Label showExpensOfText;
    @FXML
    private Button editEventNameLabel;
    @FXML
    private Button editBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn1;
    @FXML
    private Button addBtn1;
    @FXML
    private Button settleDebtsBtn;
    @FXML
    private Button sendInviteBtn;
    @FXML
    private Button allBtn;
    @FXML
    private Button deleteAllBtn;
    @FXML
    private ImageView imageview;
    @FXML
    private ComboBox<String> comboBoxOne;
    private ArrayList<String> names = new ArrayList<>(); //Names of the participants
    @FXML
    private Text eventName;
    /** TABLE PARTICIPANTS **/
    @FXML
    private TableView<Participant> tablePart;
    @FXML
    private TableColumn<Participant, String> colName;
    private ObservableList<Participant> partdata;
    /** TABLE EXPENSES **/
    @FXML
    private TableView<Expense> tableExp;
    @FXML
    private TableColumn<Expense, String> colDate;
    @FXML
    private TableColumn<Expense, String> colAm;
    @FXML
    private TableColumn<Expense, String> colPart;
    @FXML
    private TableColumn<Expense, String> colTitle;
    @FXML
    private TableColumn<Expense, String> colTag;
    private ObservableList<Expense> expdata;

    /**
     * Constructor of the EventoverviewCtrl
     * @param mc represent the MainCtrl
     * @param jsonReader is an instance of the ReadJSON class, so it can read JSONS
     * @param server server
     * @param partServer participant server
     * @param expServer expenses server
     */
    @Inject
    public EventOverviewCtrl(EventServerUtils server, MainCtrl mc, ReadJSON jsonReader, ParticipantsServerUtil partServer, ExpensesServerUtils expServer) {
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
        comboboxLanguage.getItems().addAll(languages);
        comboboxLanguage.setOnAction(event -> {
            languageChange(comboboxLanguage);
            comboboxLanguage.setPromptText("Current language: " + comboboxLanguage.getSelectionModel().getSelectedItem());

            try {
                mc.ltest();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mc.showEventOverview(Long.toString(eventid));
        });

        colName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));

        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        colDate.setCellValueFactory(q -> new SimpleStringProperty(formatter.format(q.getValue().getDate())));
        colAm.setCellValueFactory(q -> new SimpleStringProperty(String.valueOf(q.getValue().getAmount())));
        colPart.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getCreditor().getName()));
        colTitle.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getTitle()));
        colTag.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getTag()));

        tablePart.setOnMouseClicked(event -> {
            Participant selectedItem = tablePart.getSelectionModel().getSelectedItem();
            if(selectedItem.getName() == null){
                return;
            }
            mc.showEditParticipantByRow(String.valueOf(eventid), selectedItem);
        });

        tableExp.setOnMouseClicked(event -> {
            Expense selectedItem = tableExp.getSelectionModel().getSelectedItem();
            if(selectedItem.getTitle() == null){
                return;
            }
            mc.showEditExpenseByRow(String.valueOf(eventid), selectedItem);
        });

        comboBoxOne.setOnAction(event -> {
            String name = comboBoxOne.getValue();
            if(name == null){
                return;
            } else {
                Participant participant = partServer.getAllParticipants().stream().filter(participant1 -> participant1.getName().equals(name)).findAny().get();
                showExpensesOnly(participant);
            }
        });

        imageview.setImage(new Image("images/logo-no-background.png"));
        imgSet.setImage(new Image("images/settings.png"));
        imgArrow.setImage(new Image("images/arrow.png"));
        imgHome.setImage(new Image("images/home.png"));
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language that the user wants to switch to
     */
    @Override
    public void langueageswitch(String taal) {
        String langfile = "language" + taal + ".json";
        HashMap<String, Object> h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        comboboxLanguage.setPromptText("Current language: " + taal);
        Image imageFlag = new Image(h.get("key0").toString());
        imageviewFlag.setImage(imageFlag);

        partictext.setText(h.get("key7").toString());
        expenstext.setText(h.get("key8").toString());
        editBtn.setText(h.get("key9").toString());
        addBtn.setText(h.get("key10").toString());
        editBtn1.setText(h.get("key9").toString());
        addBtn1.setText(h.get("key10").toString());
        settleDebtsBtn.setText(h.get("key11").toString());
        sendInviteBtn.setText(h.get("key12").toString());
        allBtn.setText(h.get("key13").toString());
        deleteAllBtn.setText(h.get("key15").toString());
        showExpensOfText.setText(h.get("key37").toString());
        editEventNameLabel.setText(h.get("key38").toString());
        colDate.setText(h.get("key41").toString());
        colPart.setText(h.get("key43").toString());
        colAm.setText(h.get("key42").toString());
        colTitle.setText(h.get("key44").toString());
        colTag.setText(h.get("key45").toString());
        colName.setText(h.get("key46").toString());

    }

    /**
     * Method of the only button, when pressed, it shows only the expenses of the given participant
     * @param participant the participant
     */
    public void showExpensesOnly(Participant participant){
        Event x = server.getEventByID(eventid);
        var allExpenses = expServer.getExpenses().stream().filter(e -> e.getEvent().equals(x)).collect(Collectors.toList());
        var expensesOnlyParticipant = allExpenses.stream().filter(e -> e.getCreditor().getName().equals(participant.getName())).collect(Collectors.toList());
        expdata = FXCollections.observableList(expensesOnlyParticipant);
        tableExp.setItems(expdata);
    }

    /**
     * Method of the only button, when pressed, it shows all the expenses
     */
    public void showAllExpenses(){
        Event x = server.getEventByID(eventid);
        var allExpenses = expServer.getExpenses().stream().filter(e -> e.getEvent().equals(x)).collect(Collectors.toList());
        expdata = FXCollections.observableList(allExpenses);
        tableExp.setItems(expdata);
        comboBoxOne.setValue(null);
    }

    /**
     * Method of the delete all button, when pressed, it deletes all expenses
     */
    public void deleteAll(){
        int choice = JOptionPane.showOptionDialog(null,"Are you sure you want to delete all expenses?", "Delete Confirmation",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
                new String[]{"Delete", "Cancel"}, "default");

        if(choice == JOptionPane.OK_OPTION){
            Event x = server.getEventByID(eventid);
            var allExpenses = expServer.getExpenses().stream().filter(e -> e.getEvent().equals(x)).collect(Collectors.toList());
            for(Expense e : allExpenses){
                expServer.deleteExpenseByID(e.getId());
            }
            JOptionPane.showMessageDialog(null, "All Expenses Deleted");
            update(String.valueOf(eventid));
        }
    }

    /**
     * Method of the invite button, when pressed, it shows the invite screen
     */
    public void clickInvite() {
        mc.showInvite(String.valueOf(eventid));
    }

    /**
     * Method of the add button, when pressed, it shows the add expense screen
     */
    public void clickAddExpense() {
        mc.showExpense(String.valueOf(eventid));
    }

    /**
     * Method of the add button, when pressed, it shows the add participant screen
     */
    public void clickAddParticipant() {
        mc.showParticipant(String.valueOf(eventid));
    }

    /**
     * Method of the edit button, when pressed, it shows the edit participant screen
     */
    public void clickEditParticipant() {
        mc.showEditParticipant(String.valueOf(eventid));
    }

    /**
     * Method of the edit button, when pressed, it shows the edit expense screen
     */
    public void clickEditExpense() {
        mc.showEditExpense(String.valueOf(eventid));
    }

    /**
     * Method of the edit event name button, when pressed, it shows the edit event name dialog
     */
    public void editEventName(){
        String oldName = eventName.getText();
        String newName = "";

        while(true){
            newName = JOptionPane.showInputDialog("Enter the new name of the event");
            if (newName == null || newName.equals(oldName)) {
                System.out.println("Operation cancelled by the user. Name remains unchanged.");
                return;
            } else if (newName.isBlank()) {
                JOptionPane.showMessageDialog(null, "Name cannot be empty.");
            } else if(checkDuplicateName(newName)){
                JOptionPane.showMessageDialog(null, "Not allowed. Duplicate name detected.");
            } else {
                Event x = server.getEventByID(eventid);
                x.setName(newName);
                server.updateEventByID(eventid, x);

                eventName.setText(server.getEventByID(eventid).getName());
                System.out.println("Name of event(id " + eventid + ") changed from " + oldName + " to " + newName);
                break;
            }
        }
    }

    /**
     * This method checks if the name is a duplicate
     * @param name the name of the event
     * @return true if the name is a duplicate, false if the name is not a duplicate
     */
    public boolean checkDuplicateName(String name){
        List<Event> allEvents = server.getAllEvents();
        List<String> namesOfAllEvents = new ArrayList<>();
        for(Event e : allEvents){ namesOfAllEvents.add(e.getName());}
        return namesOfAllEvents.contains(name);
    }

    /**
     * Updates the page with the right information
     * @param id the id of the event
     */
    public void update(String id){
        long eid = Long.parseLong(id);
        this.eventid = eid;
        Event x = server.getEventByID(eid);
        eventName.setText(x.getName());

        List<Participant> listAllParticipants = partServer.getAllParticipants()
                .stream().filter(participant -> participant.getEvent().getId() == eventid).collect(Collectors.toList());
        names = (ArrayList<String>) listAllParticipants.stream().map(Participant::getName).collect(Collectors.toList());
        comboBoxOne.getItems().clear();
        comboBoxOne.getItems().addAll(names);

        showAllExpenses();

        var participants = partServer.getAllParticipants().stream().filter(participant -> participant.getEvent().equals(x)).collect(Collectors.toList());
        partdata = FXCollections.observableList(participants);
        tablePart.setItems(partdata);
    }

    /**
     * Used to get back to the Start Screen
     * @throws IOException if something went wrong with showing the start screen
     */
    public void clickHome() throws IOException {
        mc.showStart();
    }

    /**
     * Used to get back to the Screen before this
     * @throws IOException if something went wrong with showing the start screen
     */
    public void clickBack() throws IOException {
        mc.showStart();
    }
}