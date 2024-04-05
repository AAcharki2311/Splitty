package client.scenes;

import client.utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Event;
import commons.Expense;
import commons.Participant;
import commons.Payment;
import jakarta.inject.Inject;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdminDashboardCtrl implements Initializable {

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
    @FXML
    private TextField inputid;
    @FXML
    private TextField inputimport;
    @FXML
    private TableView<Event> table;
    @FXML
    private TableColumn<Event, String> colId;
    @FXML
    private TableColumn<Event, String> colName;
    @FXML
    private TableColumn<Event, String> colDate1;
    @FXML
    private TableColumn<Event, String> colDate2;
    @FXML
    private ImageView imageview;
    private ObservableList<Event> data;
    @FXML
    private Text welcomeText;
    @FXML
    private Text title1Text;
    @FXML
    private Text title2Text;
    @FXML
    private Text title3Text;
    @FXML
    private Button importBtn;
    @FXML
    private Button showBtn;

    /**
     * Constructor for the AdminDashboardCtrl
     *
     * @param server     server
     * @param mc         the main controller
     * @param expServer
     * @param expPart
     * @param expPay
     * @param jsonReader
     */
    @Inject
    public AdminDashboardCtrl(EventServerUtils server, MainCtrl mc, ExpensesServerUtils expServer, ParticipantsServerUtil expPart, PaymentsServerUtils expPay, ReadJSON jsonReader) {
        this.mc = mc;
        this.server = server;
        this.expServer = expServer;
        this.expPart = expPart;
        this.expPay = expPay;
        this.jsonReader = jsonReader;
    }
    /**
     * text
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
        colId.setCellValueFactory(q -> new SimpleStringProperty(String.valueOf(q.getValue().id)));
        colName.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().name));
        colDate1.setCellValueFactory(q -> new SimpleStringProperty(formatter.format(q.getValue().creationDate)));
        colDate2.setCellValueFactory(q -> new SimpleStringProperty(formatter.format(q.getValue().lastActDate)));
        refresh();

        server.registerForUpdates(event -> {
                data.removeIf(existingEvent -> existingEvent.getId() == event.getId());
                data.add(event);
        });

        imageviewFlag.setImage(new Image("images/br-circle-01.png"));
        imageview.setImage(new Image("images/logo-no-background.png"));
        imgSet.setImage(new Image("images/settings.png"));
        imgArrow.setImage(new Image("images/arrow.png"));
        imgHome.setImage(new Image("images/home.png"));
    }

    /**
     * Stops the thread
     */
    public void stop(){
        server.stop();
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
        mc.showAdminLogin();
    }

    /**
     * Goes to a specific event page
     * @throws IOException
     */
    public void clickEvent() throws IOException, InterruptedException {
        String eid = inputid.getText();
        if (eid.equals(null) || eid.equals("")){
            imgMessage.setImage(new Image("images/notifications/Slide2.png"));
            PauseTransition pause = new PauseTransition(Duration.seconds(6));
            pause.setOnFinished(e -> imgMessage.setImage(null));
            pause.play();
            return;
        }
        // if statement to check if the event does exist
        try {
            Event test = server.getEventByID(Long.parseLong(eid));
        }
        catch (Exception e){
            imgMessage.setImage(new Image("images/notifications/Slide1.png"));
            PauseTransition pause = new PauseTransition(Duration.seconds(6));
            pause.setOnFinished(p -> imgMessage.setImage(null));
            pause.play();
            return;
        }
        mc.showAdminEvent(eid);
    }

    /**
     * Imports a json file and adds it
     * @throws IOException
     */
    public void clickImport() throws IOException {
        try {

            ObjectMapper objectMapper = new ObjectMapper();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getDefaultDirectory());
            fileChooser.setTitle("Splitty23 - Import Event");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("all Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile == null || !selectedFile.getName().endsWith(".json")){
                throw new IOException();
            }

            List<Object> objects = Arrays.asList(objectMapper.readValue(selectedFile, Object[].class));

            try {
                Event myObject = objectMapper.readValue(objectMapper.writeValueAsString(objects.get(0)), Event.class);
                for (int i = 0; i < objects.size(); i++){
                    extracted(i, objectMapper, objects, myObject);
                }
            }
            catch (Exception e){
                throw new IOException();
                // System.out.println("SOMETHING WRONG WHILE PRINTING");
            }

            imgMessage.setImage(new Image("images/notifications/Slide5.png"));
            PauseTransition pause = new PauseTransition(Duration.seconds(6));
            pause.setOnFinished(p -> imgMessage.setImage(null));
            pause.play();
            refresh();
            return;
        }
        catch (Exception e){
            imgMessage.setImage(new Image("images/notifications/Slide4.png"));
            PauseTransition pause = new PauseTransition(Duration.seconds(6));
            pause.setOnFinished(p -> imgMessage.setImage(null));
            pause.play();
            return;
        }
    }

    /**
     * Extracted method of click import to reduce cyclomatic complexity
     * @param i the number from the counter
     * @param objectMapper the object mapper
     * @param objects the list with the information from the Json file
     * @param myObject the Event which the Json file is about
     * @throws JsonProcessingException
     */
    private void extracted(int i, ObjectMapper objectMapper, List<Object> objects, Event myObject) throws JsonProcessingException {
        if (i == 0){
            String json = objectMapper.writeValueAsString(objects.get(0));
            Event newEvent = server.addEvent(myObject);
            newEvent.setId(myObject.getId());
        }
        else if (i == 1){
            String json = objectMapper.writeValueAsString(objects.get(1));
            List<Participant> parts = objectMapper.readValue(json, new TypeReference<List<Participant>>(){});
            for (Participant part : parts){
                Participant newPart = expPart.addParticipant(part);
            }
        }
        else if (i == 2){
            String json = objectMapper.writeValueAsString(objects.get(2));
            List<Payment> payms = objectMapper.readValue(json, new TypeReference<List<Payment>>(){});
            if (payms != null){
                for (Payment parm : payms){
                    System.out.println("PAYMENT: " + parm);
                    Payment newPay = expPay.addPayments(parm);
                }
            }
        }
        else if (i == 3){
            String json = objectMapper.writeValueAsString(objects.get(3));
            List<Expense> expenses = objectMapper.readValue(json, new TypeReference<List<Expense>>(){});
            if (expenses != null){
                for (Expense exp : expenses){
                    Expense newExp = expServer.addExpense(exp);
                }
            }
        }
    }

    /**
     * Refreshes the data in the tables
     */
    public void refresh(){
        var events = server.getAllEvents();
        data = FXCollections.observableList(events);
        table.setItems(data);
    }

    /**
     * Changes the text on the page to the appropriate language
     * @param taal the language to change to
     */
    public void langueageswitch(String taal) {
        try {
            String langfile = "language" + taal + ".json";
            h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/" + langfile);
            welcomeText.setText(h.get("key96"));
            title1Text.setText(h.get("key102"));
            title2Text.setText(h.get("key103"));
            title3Text.setText(h.get("key104"));
            colName.setText(h.get("key31"));
            colDate1.setText(h.get("key100"));
            colDate2.setText(h.get("key101"));
            inputid.setPromptText(h.get("key94"));
            inputimport.setPromptText(h.get("key105"));
            importBtn.setText(h.get("key106"));
            showBtn.setText(h.get("key107"));
            Image imageFlag = new Image(h.get("key0"));
            imageviewFlag.setImage(imageFlag);
        }
        catch (Exception e) {
            // System.out.println("error");
            e.printStackTrace();
        }
    }

    /**
     * Method of the settings button, when pressed, it shows the keyboard combo's
     */
    public void clickSettings() {
        mc.help();
    }
}
