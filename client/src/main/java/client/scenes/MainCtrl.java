package client.scenes;

import commons.Expense;
import commons.Participant;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.FileInputStream;
import java.io.IOException;
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
    private StartScreenCtrl startCtrl;
    private EventOverviewCtrl eventOCtrl;
    private InviteCtrl inviteCtrl;
    private ParticipantCtrl participantCtrl;
    private EditParticipantCtrl editParticipantCtrl;
    private EditExpenseCtrl editExpenseCtrl;
    private AddExpenseCtrl addExpenseCtrl;
    private EventOverviewAdminCtrl eventOverviewAdminCtrl;
    private AdminDashboardCtrl adminDashboardCtrl;

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
     * @throws IOException if something is wrong with the JSON file
     */
    public void initialize(Stage primaryStage,
                           Pair <StartScreenCtrl, Parent> start,
                           Pair <EventOverviewCtrl, Parent> eventO, Pair <InviteCtrl, Parent> invite,
                           Pair <AddExpenseCtrl, Parent> addExpense, Pair <ParticipantCtrl, Parent> participant,
                           Pair <EditParticipantCtrl, Parent> editParticipant, Pair <EditExpenseCtrl, Parent> editExpense,
                           Pair <AdminLoginCtrl, Parent> alogin, Pair <AdminDashboardCtrl, Parent> adash,
                           Pair <EventOverviewAdminCtrl, Parent> aevent) throws IOException {
        this.primaryStage = primaryStage;
        this.startScene = new Scene(start.getValue());
        this.eventOverviewScene = new Scene(eventO.getValue());
        this.inviteScene = new Scene(invite.getValue());
        this.expenseScene = new Scene(addExpense.getValue());
        this.participantScene = new Scene(participant.getValue());
        this.editParticipantScene = new Scene(editParticipant.getValue());
        this.editExpenseScene = new Scene(editExpense.getValue());

        this.startCtrl = start.getKey();
        this.eventOCtrl = eventO.getKey();
        this.inviteCtrl = invite.getKey();
        this.participantCtrl = participant.getKey();
        this.editParticipantCtrl = editParticipant.getKey();
        this.editExpenseCtrl = editExpense.getKey();
        this.addExpenseCtrl = addExpense.getKey();

        ltest();

//__________________ADMIN PAGES____________________________________________________________________________

        this.aloginScene = new Scene(alogin.getValue());
        this.adashScene = new Scene(adash.getValue());
        this.aeventScene = new Scene(aevent.getValue());
        this.eventOverviewAdminCtrl = aevent.getKey();
        this.adminDashboardCtrl = adash.getKey();

        showStart();
        primaryStage.show();
    }

    /**
     * Method to change the language
     * @return the language to be used
     * @throws IOException if someting is wrong with the JSON file
     */
    public String setLanguage() throws IOException {
        Properties appProps = new Properties();
        String configFilePath = "src/main/resources/configfile.properties";

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
        String languageToTranslate = setLanguage();

        this.startCtrl.langueageswitch(languageToTranslate);
        this.eventOCtrl.langueageswitch(languageToTranslate);
        this.inviteCtrl.langueageswitch(languageToTranslate);
        this.participantCtrl.langueageswitch(languageToTranslate);
        this.editParticipantCtrl.langueageswitch(languageToTranslate);
        this.editExpenseCtrl.langueageswitch(languageToTranslate);
        this.addExpenseCtrl.langueageswitch(languageToTranslate);

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
        primaryStage.setTitle("EventOverview");
        primaryStage.setScene(eventOverviewScene);
        eventOCtrl.update(id);
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
     * Shows the admin login screen
     */
    public void showAdminLogin () {
        primaryStage.setTitle("Admin Login");
        primaryStage.setScene(aloginScene);
    }

    /**
     * Shows the admin dashboard screen
     */
    public void showAdminDashboard() {
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(adashScene);
        adminDashboardCtrl.refresh();
    }

    /**
     * Show the event information for admins
     * @param id the id of the event
     */
    public void showAdminEvent(String id){
        primaryStage.setTitle("Admin Event view");
        primaryStage.setScene(aeventScene);
        eventOverviewAdminCtrl.update(id);
    }

    /**
     * Sets the participant
     * @param p the participant
     */
    public void setParticipant(Participant p){
        participantCtrl.setUserParticipant(p);
    }
}