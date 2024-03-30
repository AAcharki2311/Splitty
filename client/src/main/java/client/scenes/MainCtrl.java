package client.scenes;

import commons.Expense;
import commons.Participant;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class MainCtrl {
    private Stage primaryStage;
    private Scene startScene;
    private Scene eventOverviewScene;
    private Scene inviteScene;
    private Scene expenseScene;
    private Scene participantScene;
    private Scene editParticipantScene;
    private Scene editExpenseScene;
    private Scene aloginScene;
    private Scene adashScene;
    private Scene aeventScene;
    private Scene settleScene;
    /**CONTROLLERS**/
    private StartScreenCtrl startCtrl;
    private EventOverviewCtrl eventOCtrl;
    private InviteCtrl inviteCtrl;
    private ParticipantCtrl participantCtrl;
    private EditParticipantCtrl editParticipantCtrl;
    private EditExpenseCtrl editExpenseCtrl;
    private AddExpenseCtrl addExpenseCtrl;
    private EventOverviewAdminCtrl eventOverviewAdminCtrl;
    private AdminDashboardCtrl adminDashboardCtrl;
    private SettleDebtsCtrl settleDebtsCtrl;
    private AdminLoginCtrl adminLoginCtrl;

    /**
     * Initializes all the controllers
     * @param primaryStage The primary stage
     * @param start controller for the start screen
     * @param eventO controller for the screen with an overview of the event
     * @param invite controller for the screen where people can be invited to an event
     * @param addExpense controller for the screen where an expense can be added for an event
     * @param participant controller for the screen where a participant can be added
     * @param editParticipant controller the screen where a participant can be changed
     * @param editExpense controller for the screen where expenses can be edited
     * @param alogin controller for the admin login screen
     * @param adash controller for the admin dashboard screen
     * @param aevent controller for the admin event view screen
     * @param settle controller for the settle debts screen
     * @throws IOException if something is wrong with the JSON file
     */
    public void initialize(Stage primaryStage,
                           Pair <StartScreenCtrl, Parent> start,
                           Pair <EventOverviewCtrl, Parent> eventO, Pair <InviteCtrl, Parent> invite,
                           Pair <AddExpenseCtrl, Parent> addExpense, Pair <ParticipantCtrl, Parent> participant,
                           Pair <EditParticipantCtrl, Parent> editParticipant, Pair <EditExpenseCtrl, Parent> editExpense,
                           Pair <AdminLoginCtrl, Parent> alogin, Pair <AdminDashboardCtrl, Parent> adash,
                           Pair <EventOverviewAdminCtrl, Parent> aevent, Pair <SettleDebtsCtrl, Parent> settle) throws IOException {
        this.primaryStage = primaryStage;
        this.startScene = new Scene(start.getValue());
        this.eventOverviewScene = new Scene(eventO.getValue());
        this.inviteScene = new Scene(invite.getValue());
        this.expenseScene = new Scene(addExpense.getValue());
        this.participantScene = new Scene(participant.getValue());
        this.editParticipantScene = new Scene(editParticipant.getValue());
        this.editExpenseScene = new Scene(editExpense.getValue());
        this.settleScene = new Scene(settle.getValue());

        this.startCtrl = start.getKey();
        this.eventOCtrl = eventO.getKey();
        this.inviteCtrl = invite.getKey();
        this.participantCtrl = participant.getKey();
        this.editParticipantCtrl = editParticipant.getKey();
        this.editExpenseCtrl = editExpense.getKey();
        this.addExpenseCtrl = addExpense.getKey();
        this.settleDebtsCtrl = settle.getKey();

//__________________ADMIN PAGES____________________________________________________________________________

        this.aloginScene = new Scene(alogin.getValue());
        this.adashScene = new Scene(adash.getValue());
        this.aeventScene = new Scene(aevent.getValue());
        this.eventOverviewAdminCtrl = aevent.getKey();
        this.adminDashboardCtrl = adash.getKey();
        this.adminLoginCtrl = alogin.getKey();

        ltest();

        showStart();
        setUpKeyboardShortcuts();
        primaryStage.show();
    }

    /**
     * Sets up the keyboard shortcuts
     */
    public void setUpKeyboardShortcuts() {
        if(primaryStage != null){
            startScene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            eventOverviewScene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            inviteScene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            expenseScene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            participantScene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            editParticipantScene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            editExpenseScene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            settleScene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
        }
    }

    /**
     * Method to handle the key press
     * @param keyEvent the key event
     */
    public void handleKeyPress(KeyEvent keyEvent) {
        if(keyEvent.isAltDown()) {
            Scene currentScene = primaryStage.getScene();
            switch (keyEvent.getCode()) {
                case A:
                    if (currentScene == eventOverviewScene) eventOCtrl.clickAddParticipant();
                    break;
                case C:
                    if (currentScene == startScene) startCtrl.createEvent();
                    break;
                case E:
                    if (currentScene == eventOverviewScene) eventOCtrl.clickEditParticipant();
                    break;
                case F:
                    if (currentScene == eventOverviewScene) eventOCtrl.clickSettleDebts();
                    break;
                case I:
                    if (currentScene == eventOverviewScene) eventOCtrl.clickInvite();
                    break;
                case J:
                    if (currentScene == startScene) startCtrl.openEvent();
                    break;
                case K:
                    if (currentScene == eventOverviewScene) eventOCtrl.clickAddExpense();
                    break;
                case L:
                    if (currentScene == eventOverviewScene) eventOCtrl.clickEditExpense();
                    break;
                case S:
                    TableView<String> tableView = setupTable();

                    Stage newStage = new Stage();
                    newStage.setTitle("Shortcuts");

                    StackPane secondaryLayout = new StackPane();
                    secondaryLayout.getChildren().addAll(tableView);

                    Scene secondscene = new Scene(secondaryLayout, 285, 300);
                    newStage.setScene(secondscene);
                    newStage.show();
                    break;
                case Y:
                    System.exit(0);
                case SLASH:
                    keySlashMethod(currentScene);
                    break;
                default:
                    break;
            }
            keyEvent.consume();
        }
    }

    /**
     * Shows the keyboard combo's
     */
    public void help() {
        TableView<String> tableView = setupTable();

        Stage newStage = new Stage();
        newStage.setTitle("Shortcuts");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().addAll(tableView);

        Scene secondscene = new Scene(secondaryLayout, 285, 300);
        newStage.setScene(secondscene);
        newStage.show();
    }

    private TableView<String> setupTable() {
        ObservableList<String> data = FXCollections.observableArrayList(
                "Alt + A, Add participant, Event Overview",
                "Alt + C, Create event, Start",
                "Alt + E, Edit participant, Event Overview",
                "Alt + F, Settle debts, Event Overview",
                "Alt + I, Invite, Event Overview",
                "Alt + J, Join event, Start",
                "Alt + K, Add expense, Event Overview",
                "Alt + L, Edit expense, Event Overview",
                "Alt + S, Show shortcuts, All",
                "Alt + Y, Close application, All",
                "Alt + /, Go back, All"
        );

        TableView<String> tableView = new TableView<>();
        tableView.setItems(data);

        TableColumn<String, String> colShortcut = new TableColumn<>("Shortcut");
        TableColumn<String, String> colAction = new TableColumn<>("Action");
        TableColumn<String, String> colPage = new TableColumn<>("Page");

        colShortcut.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().split(",")[0].trim()));
        colAction.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().split(",")[1].trim()));
        colPage.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().split(",")[2].trim()));

        tableView.getColumns().addAll(colShortcut, colAction, colPage);
        return tableView;
    }

    /**
     * Method to handle the key press for the slash key
     * @param currentScene the current scene
     */
    private void keySlashMethod(Scene currentScene) {
        if(currentScene == eventOverviewScene) {
            eventOCtrl.clickBack();
        } else if (currentScene == inviteScene) {
            inviteCtrl.clickBack();
        } else if (currentScene == expenseScene) {
            addExpenseCtrl.clickBack();
        } else if (currentScene == participantScene) {
            participantCtrl.clickBack();
        } else if (currentScene == editParticipantScene) {
            editParticipantCtrl.clickBack();
        } else if (currentScene == editExpenseScene) {
            editExpenseCtrl.clickBack();
        } else if (currentScene == settleScene) {
            settleDebtsCtrl.clickBack();
        }
    }

    /**
     * Method to change the language
     * @param configFilePath the path to the config file
     * @return the language to be used
     * @throws IOException if someting is wrong with the JSON file
     */
    public String setLanguage(String configFilePath) throws IOException {
        Properties appProps = new Properties();

        FileInputStream inputStream = new FileInputStream(configFilePath);
        appProps.load(inputStream);
        inputStream.close();

        String configtaal = appProps.getProperty("language");

        String languageJSON;
        switch(configtaal){
            case "dutch", "Dutch":
                languageJSON = "NL";
                return languageJSON;
            case "french", "French":
                languageJSON = "FR";
                return languageJSON;
            default:
                languageJSON = "EN";
                return languageJSON;
        }
    }

    /**
     * Changes the language for every screen
     * @throws IOException if something is wrong with the JSON file
     */
    public void ltest() throws IOException {
        String languageToTranslate = setLanguage("src/main/resources/configfile.properties");

        this.startCtrl.langueageswitch(languageToTranslate);
        this.eventOCtrl.langueageswitch(languageToTranslate);
        this.inviteCtrl.langueageswitch(languageToTranslate);
        this.participantCtrl.langueageswitch(languageToTranslate);
        this.editParticipantCtrl.langueageswitch(languageToTranslate);
        this.editExpenseCtrl.langueageswitch(languageToTranslate);
        this.addExpenseCtrl.langueageswitch(languageToTranslate);
        this.settleDebtsCtrl.langueageswitch(languageToTranslate);
    }

    /**
     * Shows the start screen
     */
    public void showStart() {
        primaryStage.setTitle("Splitty 23");
        primaryStage.setScene(startScene);
        startCtrl.reset();
    }

    /**
     * Shows the event overview screen
     * @param id the id of the event
     */
    public void showEventOverview(String id) {
        eventOCtrl.update(id);
        primaryStage.setTitle("EventOverview");
        primaryStage.setScene(eventOverviewScene);
    }

    /**
     * Shows the invite screen
     * @param id the id of the event
     */
    public void showInvite(String id) {
        primaryStage.setTitle("Invite");
        primaryStage.setScene(inviteScene);
        inviteCtrl.update(id);
    }

    /**
     * Shows the add expenses screen
     * @param id the id of the event
     */
    public void showExpense(String id) {
        primaryStage.setTitle("Add Expense");
        primaryStage.setScene(expenseScene);
        addExpenseCtrl.update(id);
    }

    /**
     * Shows the add participant screen
     * @param id the id of the event
     */
    public void showParticipant(String id) {
        primaryStage.setTitle("Add Participant");
        primaryStage.setScene(participantScene);
        participantCtrl.update(id);
    }

    /**
     * Shows the edit participant screen
     * @param id the id of the event
     */
    public void showEditParticipant(String id) {
        primaryStage.setTitle("Edit Participant");
        primaryStage.setScene(editParticipantScene);
        editParticipantCtrl.update(id);
    }

    /**
     * Shows the edit participant screen when clicked on Row
     * @param id the id of the event
     * @param participant the selected participant
     */
    public void showEditParticipantByRow(String id, Participant participant) {
        primaryStage.setTitle("Edit Participant");
        primaryStage.setScene(editParticipantScene);
        editParticipantCtrl.update(id);
        editParticipantCtrl.setComboBoxParticipants(participant);
    }

    /**
     * Shows the edit expense screen
     * @param id the id of the event
     */
    public void showEditExpense(String id) {
        primaryStage.setTitle("Edit Expense");
        primaryStage.setScene(editExpenseScene);
        editExpenseCtrl.update(id);
    }

    /**
     * Shows the edit expense screen when clicked on Row
     * @param id the id of the event
     * @param expense the selected expense
     */
    public void showEditExpenseByRow(String id, Expense expense) {
        primaryStage.setTitle("Edit Expense");
        primaryStage.setScene(editExpenseScene);
        editExpenseCtrl.update(id);
        editExpenseCtrl.setComboBoxExpenses(expense);
    }

    /**
     * Shows the settle debts screen
     * @param id the id of the event
     */
    public void showSettleDebts (String id) {
        primaryStage.setTitle("Settle Debts");
        primaryStage.setScene(settleScene);
        settleDebtsCtrl.update(id);
    }

    /**
     * Shows the admin login screen
     */
    public void showAdminLogin () {
        try {
            String taal = setLanguage("src/main/resources/configfile.properties");
            adminLoginCtrl.langueageswitch(taal);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        primaryStage.setTitle("Admin Login");
        primaryStage.setScene(aloginScene);
    }

    /**
     * Shows the admin dashboard screen
     */
    public void showAdminDashboard() {
        try {
            String taal = setLanguage("src/main/resources/configfile.properties");
            adminDashboardCtrl.langueageswitch(taal);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        adminDashboardCtrl.refresh();
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(adashScene);

    }

    /**
     * Show the event information for admins
     * @param id the id of the event
     */
    public void showAdminEvent(String id){
        try {
            String taal = setLanguage("src/main/resources/configfile.properties");
            eventOverviewAdminCtrl.langueageswitch(taal);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        eventOverviewAdminCtrl.update(id);
        primaryStage.setTitle("Admin Event view");
        primaryStage.setScene(aeventScene);
    }

    /**
     * Sets the participant
     * @param p the participant
     */
    public void setParticipant(Participant p){
        participantCtrl.setUserParticipant(p);
    }
    /**
     * Sets the changed expenses
     * @param tempList the list of changed expenses
     */
    public void setTempList(ArrayList<Expense> tempList) {
        editExpenseCtrl.setChangedExpenses(tempList);
    }

    /**
     * Gets the changed expenses
     * @return the list of changed expenses
     */
    public ArrayList<Expense> getTempList() {
        return editExpenseCtrl.getChangedExpenses();
    }
}