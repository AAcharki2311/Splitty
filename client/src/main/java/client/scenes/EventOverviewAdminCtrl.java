package client.scenes;

import client.utils.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import commons.Participant;
import commons.Payment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class EventOverviewAdminCtrl implements Initializable {

    /** BASIS **/
    private final EventServerUtils server;
    private final MainCtrl mc;
    private final ExpensesServerUtils expServer;
    private final ParticipantsServerUtil expPart;
    private final PaymentsServerUtils expPay;
    private final ReadJSON jsonReader;
    private HashMap<String, String> h;
    /** MENU **/
    @FXML
    private ImageView imgSet;
    @FXML
    private ImageView imgArrow;
    @FXML
    private ImageView imgHome;
    @FXML
    private ImageView imageviewFlag;
    /** PAGE **/
    @FXML
    private ImageView imgMessage;
    private long eventid;
    @FXML
    private Text eventname;
    @FXML
    private ImageView imageview;
    @FXML
    private Button jsonBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Text expText;
    @FXML
    private Text parText;
    private String taal;
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
    /** TABLE PARTICIPANTS **/
    @FXML
    private TableView<Participant> tablePart;
    @FXML
    private TableColumn<Participant, String> colName;
    @FXML
    private TableColumn<Participant, String> colEmail;
    private ObservableList<Participant> partdata;

    /**
     * Constructer for the AdminEvent Controller
     *
     * @param server     the connection with the EventServerUtils class
     * @param expServer  the connection with the ExpensesServerUtils class
     * @param expPart    the connection with the ParticipantServerUtils class
     * @param expPay     the connection with the PaymentServerUtils class
     * @param m          the main controller
     * @param jsonReader the ReadJSON object
     */
    @Inject
    public EventOverviewAdminCtrl(EventServerUtils server, ExpensesServerUtils expServer,
                                  ParticipantsServerUtil expPart, PaymentsServerUtils expPay, MainCtrl m, ReadJSON jsonReader) {
        this.mc = m;
        this.server = server;
        this.expServer = expServer;
        this.expPart = expPart;
        this.expPay = expPay;
        this.jsonReader = jsonReader;
        this.eventid = 0;
    }

    /**
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        colDate.setCellValueFactory(q -> new SimpleStringProperty(formatter.format(q.getValue().getDate())));
        colAm.setCellValueFactory(q -> new SimpleStringProperty(String.valueOf(q.getValue().getAmount())));
        colPart.setCellValueFactory(q -> new SimpleStringProperty(String.valueOf(q.getValue().getCreditor().getName())));
        colTitle.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getTitle()));
        colTag.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getTag()));
        colName.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getName()));
        colEmail.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getEmail()));
        imgSet.setImage(new Image("images/settings.png"));
        imgArrow.setImage(new Image("images/arrow.png"));
        imgHome.setImage(new Image("images/home.png"));
        imageviewFlag.setImage(new Image("images/br-circle-01.png"));
        imageview.setImage(new Image("images/logo-no-background.png"));
    }

    /**
     * Goes back to the Admin Dashboard scene
     * @throws IOException
     */
    public void clickDashboard() throws IOException {
        mc.showAdminDashboard();
    }

    /**
     * If a user clicks on delete event a pop-up shows up asking if the user is really sure.
     * If the user clicks yes the event gets deleted from the database and the user gets directed
     * back to the Admin Dashboard page
     * @throws IOException
     */
    public void clickDelete() throws IOException {
        String langfile = "language" + taal + ".json";
        h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/" + langfile);

        // delete event from the database
        Event x = server.getEventByID(eventid);

        while(true){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel(h.get("key111") + x.name + "?"));
            panel.add(new JLabel(h.get("key112")));
            Object[] options = {h.get("key57"), h.get("key58")};

            int result = JOptionPane.showOptionDialog(null, panel, "Delete event",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (result == JOptionPane.OK_OPTION) {
                expServer.getExpenses().stream()
                        .filter(e -> e.getEvent().getId() == eventid).forEach(e -> expServer.deleteExpenseByID(e.getId()));
                expPart.getAllParticipants().stream()
                        .filter(p -> p.getEvent().getId() == eventid).forEach(p -> expPart.deleteParticipantByID(p.getId()));
                server.deleteEventByID(eventid);
                mc.showAdminDashboard();
            }
            break;
        }
    }

    /**
     * This method updates the page with the right information for an event.
     * @param id id
     */
    public void update(String id) {
        long eid = Long.parseLong(id);
        this.eventid = eid;
        Event x = server.getEventByID(eid);
        // get the information from the database
        eventname.setText(x.getName());
        refresh();
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
        mc.showAdminDashboard();
    }

    /**
     * Used to refresh the table data to get updated data
     */
    public void refresh(){
        List<Expense> expenses = expServer.getExpenses();
        List<Expense> upexpenses = expenses.stream().filter(x -> x.getEvent().getId() == eventid).toList();
        expdata = FXCollections.observableList(upexpenses);
        tableExp.setItems(expdata);

        var participants = expPart.getAllParticipants();
        List<Participant> uppart = participants.stream().filter(x -> x.getEvent().getId() == eventid).toList();
        partdata = FXCollections.observableList(uppart);
        tablePart.setItems(partdata);
    }

    /**
     * Method to download a JSON file from an event. It saves the file in src/main/resources/JSONfiles
     * @throws IOException
     */
    public void clickDownload() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Create instances of Events and all data
        Event event = server.getEventByID(eventid);
        List<Participant> participants = expPart.getAllParticipants();
        List<Participant> uppart = participants.stream().filter(x -> x.getEvent().getId() == eventid).toList();
        // List<Payment> payments = expPay.getPayments();
        List<Payment> uppay = null;
        // List<Payment> uppay = payments.stream().filter(x -> x.getEvent().getId() == eventid).toList();
        List<Expense> expenses = expServer.getExpenses();
        List<Expense> upexp = expenses.stream().filter(x -> x.getEvent().getId() == eventid).toList();

        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());

        try {
            List<Object> jsonDataObject = new ArrayList<>();
            jsonDataObject.add(event);
            jsonDataObject.add(uppart);
            jsonDataObject.add(uppay);
            jsonDataObject.add(upexp);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("Event" + eventid + ".json");
            fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getDefaultDirectory());
            fileChooser.setTitle("Splitty23 - Download Event");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
            File file = fileChooser.showSaveDialog(null);
            file.createNewFile();

            writer.writeValue(file, jsonDataObject);

            JOptionPane.showOptionDialog(null, h.get("key139"),h.get("key141"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{}, null);

//            imgMessage.setImage(new Image("images/notifications/Slide5.png"));
//            PauseTransition pause = new PauseTransition(Duration.seconds(6));
//            pause.setOnFinished(p -> imgMessage.setImage(null));
//            pause.play();

        } catch (IOException e) {
//            imgMessage.setImage(new Image("images/notifications/Slide4.png"));
//            PauseTransition pause = new PauseTransition(Duration.seconds(6));
//            pause.setOnFinished(p -> imgMessage.setImage(null));
//            pause.play();
            JOptionPane.showOptionDialog(null, h.get("key131"),h.get("key132"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, new Object[]{}, null);
            e.printStackTrace();
        }
    }

    /**
     * Method of the settings button, when pressed, it shows the keyboard combo's
     */
    public void clickSettings() {
        mc.help(h);
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language to switch to
     */
    public void langueageswitch(String taal) {
        try {
            this.taal = taal;
            String langfile = "language" + taal + ".json";
            h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/" + langfile);
            Image imageFlag = new Image(h.get("key0"));
            imageviewFlag.setImage(imageFlag);
            jsonBtn.setText(h.get("key108"));
            deleteBtn.setText(h.get("key109"));
            colDate.setText(h.get("key41"));
            colAm.setText(h.get("key42"));
            colPart.setText(h.get("key43"));
            colTitle.setText(h.get("key44"));
            colTag.setText(h.get("key45"));
            colName.setText(h.get("key46"));
            colEmail.setText(h.get("key110"));
            expText.setText(h.get("key8"));
            parText.setText(h.get("key7"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class DataWrapper {
        private Event event;
        private List<Participant> parts;
        private List<Expense> exps;
        private List<Payment> pays;

        public DataWrapper(Event event, List<Participant> parts, List<Payment> pays, List<Expense> exps) {
            this.event = event;
            this.parts = parts;
            this.exps = exps;
            this.pays = pays;
        }

        /**
         * Getter for the event in the data wrapper
         * @return the event
         */
        public Event getEvent() {
            return event;
        }

        /**
         * Getter for the participants in the data wrapper
         * @return a list of participants
         */
        public List<Participant> getParts() {
            return parts;
        }

        /**
         * Getter for the expenses in the data wrapper
         * @return a list of expenses
         */
        public List<Expense> getExps() {
            return exps;
        }

        /**
         * Getter for the payments in the data wrapper
         * @return a list of payments
         */
        public List<Payment> getPays() {
            return pays;
        }
    }
}