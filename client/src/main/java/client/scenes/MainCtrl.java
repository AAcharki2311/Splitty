package client.scenes;

import client.utils.ReadURL;
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

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class MainCtrl {
    private Stage primaryStage;
    private Scene startScene;
    private Scene eventOverviewScene;
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
    private ParticipantCtrl participantCtrl;
    private EditParticipantCtrl editParticipantCtrl;
    private EditExpenseCtrl editExpenseCtrl;
    private AddExpenseCtrl addExpenseCtrl;
    private EventOverviewAdminCtrl eventOverviewAdminCtrl;
    private AdminDashboardCtrl adminDashboardCtrl;
    private SettleDebtsCtrl settleDebtsCtrl;
    private AdminLoginCtrl adminLoginCtrl;
    private String serverURL = "http://localhost:8080";

    /**
     * Initializes all the controllers
     * @param primaryStage The primary stage
     * @param start controller for the start screen
     * @param eventO controller for the screen with an overview of the event
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
                           Pair <EventOverviewCtrl, Parent> eventO,
                           Pair <AddExpenseCtrl, Parent> addExpense, Pair <ParticipantCtrl, Parent> participant,
                           Pair <EditParticipantCtrl, Parent> editParticipant, Pair <EditExpenseCtrl, Parent> editExpense,
                           Pair <AdminLoginCtrl, Parent> alogin, Pair <AdminDashboardCtrl, Parent> adash,
                           Pair <EventOverviewAdminCtrl, Parent> aevent, Pair <SettleDebtsCtrl, Parent> settle) throws IOException {
        this.primaryStage = primaryStage;
        this.startScene = new Scene(start.getValue());
        this.eventOverviewScene = new Scene(eventO.getValue());
        this.expenseScene = new Scene(addExpense.getValue());
        this.participantScene = new Scene(participant.getValue());
        this.editParticipantScene = new Scene(editParticipant.getValue());
        this.editExpenseScene = new Scene(editExpense.getValue());
        this.settleScene = new Scene(settle.getValue());

        this.startCtrl = start.getKey();
        this.eventOCtrl = eventO.getKey();
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

        serverURlpopUP();

        showStart();
        setUpKeyboardShortcuts();
        primaryStage.show();
    }

    /**
     * Method to pop up a dialog to change the server URL
     */
    public void serverURlpopUP() {
        ReadURL readURL = new ReadURL();
        while(true){
            serverURL = JOptionPane.showInputDialog("Please enter the server URL:");
            if(serverURL == null){
                System.exit(0);
            } else if (serverURL.isBlank()) {
                JOptionPane.showMessageDialog(null, "No URL entered");
            } else if (!isServerReachable(serverURL)) {
                int choice = JOptionPane.showOptionDialog(null, "Server not reachable. Do you want to use the default URL (http://localhost:8080) or try again?",
                        "Server not reachable", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        new String[]{"Use default", "Try again"}, "default");
                if(choice == 0){
                    serverURL = "http://localhost:8080";
                    readURL.writeServerUrl(serverURL, "src/main/resources/configfile.properties");
                    break;
                }
            } else{
                readURL.writeServerUrl(serverURL, "src/main/resources/configfile.properties");
                break;
            }
        }
    }

    /**
     * This method checks if the server is reachable
     * @param url the url that the user wants to switch to
     * @return true if the server is reachable, false otherwise
     */
    public boolean isServerReachable(String url) {
        try {
            URL u = new URL(url);
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            return huc.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Sets up the keyboard shortcuts
     */
    public void setUpKeyboardShortcuts() {
        if(primaryStage != null){
            startScene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            eventOverviewScene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
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
                    eventOCtrl.clickSettings();
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
     * @param h the hashmap
     */
    public void help(HashMap<String, String> h) {
        TableView<String> tableView = setupTable(h);

        Stage newStage = new Stage();
        newStage.setTitle("Shortcuts");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().addAll(tableView);

        Scene secondscene = new Scene(secondaryLayout, 400, 300);
        newStage.setScene(secondscene);
        newStage.show();
    }

    /**
     * Method to set up the table with shortcuts
     * @param h the hashmap
     * @return the table view
     */
    public TableView<String> setupTable(HashMap<String, String> h) {
        ObservableList<String> data = FXCollections.observableArrayList(
                ("Alt + A," + h.get("key113") + ", " + h.get("key120")),
                ("Alt + C," + h.get("key4") + ", Start"),
                ("Alt + E," + h.get("key114") + ", " + h.get("key120")),
                ("Alt + F," + h.get("key115") + ", " + h.get("key120")),
                ("Alt + I," + h.get("key12") + ", " + h.get("key120")),
                ("Alt + J," + h.get("key3") + ", Start"),
                ("Alt + K," + h.get("key116") + ", " + h.get("key120")),
                ("Alt + L," + h.get("key117") + ", " + h.get("key120")),
                ("Alt + S," + h.get("key118") + ", " + h.get("key13")),
                ("Alt + Y," + h.get("key119") + ", " + h.get("key13")),
                ("Alt + /," + h.get("key99") + ", " + h.get("key13"))
        );

        TableView<String> tableView = new TableView<>();
        tableView.setItems(data);

        TableColumn<String, String> colShortcut = new TableColumn<>("Shortcut");
        TableColumn<String, String> colAction = new TableColumn<>(h.get("key125"));
        TableColumn<String, String> colPage = new TableColumn<>(h.get("key126"));

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
    public void keySlashMethod(Scene currentScene) {
        if(currentScene == eventOverviewScene) {
            eventOCtrl.clickBack();
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
        startCtrl.startTimer();
    }

    /**
     * Shows the event overview screen
     * @param id the id of the event
     */
    public void showEventOverview(String id) {
        eventOCtrl.update(id);
        primaryStage.setTitle("EventOverview");
        primaryStage.setScene(eventOverviewScene);
        if(!startCtrl.stopTimer()){
            throw new RuntimeException("Timer not stopped");
        }
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
        if(!startCtrl.stopTimer()){
            throw new RuntimeException("Timer not stopped");
        }
    }

    /**
     * Shows the admin dashboard screen
     */
    public void showAdminDashboard() {
        adminDashboardCtrl.refresh();
        try {
            String taal = setLanguage("src/main/resources/configfile.properties");
            adminDashboardCtrl.langueageswitch(taal);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(adashScene);

    }

    /**
     * Show the event information for admins
     * @param id the id of the event
     */
    public void showAdminEvent(String id){
        eventOverviewAdminCtrl.refresh();
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

    /**
     * Method for getting the current event Controller
     *
     * @return the current eventController
     */
    public EventOverviewCtrl getEventOCtrl() {
        return eventOCtrl;
    }

    /**
     * Method for setting the current event Controller
     * @param eventOCtrl the event controller to set
     */
    public void setEventOCtrl(EventOverviewCtrl eventOCtrl) {
        this.eventOCtrl = eventOCtrl;
    }

    /**
     * Method for setting the current editexpense Controller
     * @param editExpenseCtrl the editexpense controller to set
     */
    public void setEditExpenseCtrl(EditExpenseCtrl editExpenseCtrl) {
        this.editExpenseCtrl = editExpenseCtrl;
    }

    /**
     * Method for setting the current participant Controller
     * @param participantCtrl the participant controller to set
     */
    public void setParticipantCtrl(ParticipantCtrl participantCtrl) {
        this.participantCtrl = participantCtrl;
    }

    /**
     * A method for sending a payload via this instance's EventOverViewCtrl's EventServerUtils
     * Used for centralised sending of payloads
     *
     * @param dest The URL to send to
     * @param o The payload to send
     */
    public void send(String dest, Object o) {
        eventOCtrl.getEventServerUtils().send(dest, o);
    }
}