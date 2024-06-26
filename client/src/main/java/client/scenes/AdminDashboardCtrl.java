package client.scenes;

import client.utils.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Event;
import commons.Expense;
import commons.Participant;
import commons.Payment;
import jakarta.inject.Inject;
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
import javax.swing.*;
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
    private Text title3Text;
    @FXML
    private Button importBtn;
    @FXML
    private Button showBtn;

    /**
     * Constructor for the AdminDashboardCtrl
     *
     * @param server        server
     * @param mc            the main controller
     * @param expServer     ExpensesServerUtils
     * @param expPart       ParticipantsServerUtil
     * @param expPay        PaymentsServerUtils
     * @param jsonReader    ReadJSON
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

        table.setOnMouseClicked(event -> {
            Event selectedItem = table.getSelectionModel().getSelectedItem();
            mc.showAdminEvent(String.valueOf(selectedItem.getId()));
        });

        server.registerForUpdates(event -> {
                data.removeIf(existingEvent -> existingEvent.getId() == event.getId());
                data.add(event);
                colId.setCellValueFactory(q -> new SimpleStringProperty(String.valueOf(q.getValue().id)));
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
     * @throws IOException if something went wrong with showing the event page
     */
    public void clickEvent() throws IOException, InterruptedException {
        String eid = inputid.getText();
        if (eid.isBlank() || !checkEvent(eid)){
            JOptionPane.showOptionDialog(null, h.get("key133"),h.get("key130"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{}, null);
//            imgMessage.setImage(new Image("images/notifications/Slide2.png"));
//            PauseTransition pause = new PauseTransition(Duration.seconds(6));
//            pause.setOnFinished(e -> imgMessage.setImage(null));
//            pause.play();
            return;
        } else{
            mc.showAdminEvent(eid);
        }
        checkEvent(eid);
    }

//     /**
//      * Shows error message 2
//      */
//     public void showError2() {
//         try {
//             String eid = inputid.getText();
//             Event test = server.getEventByID(Long.parseLong(eid));
//         }
//         catch (Exception e){
//             JOptionPane.showOptionDialog(null, h.get("key117"),h.get("key114"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{}, null);
// //            imgMessage.setImage(new Image("images/notifications/Slide1.png"));
// //            PauseTransition pause = new PauseTransition(Duration.seconds(6));
// //            pause.setOnFinished(p -> imgMessage.setImage(null));
// //            pause.play();
//             return;
//         }
//     }

    /**
     * Check for valid input
     * @param eid the input check
     * @return true or false depending on validity
     */
    public boolean checkEvent(String eid) {
        if (eid.equals(null) || eid.equals("")) {
            return false;
        } else {
            try {
                Event test = server.getEventByID(Long.parseLong(eid));
                mc.showAdminEvent(eid);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * Imports a json file and adds it
     * @throws IOException if something went wrong with importing the file
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
                Event myObject = objectMapper.readValue(objectMapper.writeValueAsString(objects.getFirst()), Event.class);
                Event newev = new Event(myObject.getName(), myObject.getCreationDate(), myObject.getLastActDate());
                for (int i = 0; i < objects.size(); i++){
                    if (i == 0){
                        String json = objectMapper.writeValueAsString(objects.get(0));
                        long id = myObject.getId();
                        newev = server.addEvent(newev);

                        HashMap<String, String> ht = jsonReader.readJsonToMap("src/main/resources/tagcolors.json");
                        ht.put("Food?"+(String.valueOf(newev.id)), "#43CE43");
                        ht.put("Entrance Fees?"+(String.valueOf(newev.id)), "#616BD0");
                        ht.put("Travel?" + (String.valueOf(newev.id)), "#D71919");
                        jsonReader.writeMapToJsonFile(ht, "src/main/resources/tagcolors.json");
                    }
                    else if (i == 1){
                        String json = objectMapper.writeValueAsString(objects.get(1));
                        List<Participant> parts = objectMapper.readValue(json, new TypeReference<List<Participant>>(){});
                        for (Participant part : parts){
                            Participant np = new Participant(newev, part.getName(), part.getEmail(), part.getIban(), part.getBic(), part.getId());
                            // System.out.println("UPDATED PARTICIPANT:" + np);
                            try {
                                expPart.addParticipant(np);
                            }
                            catch (Exception e) {
                                System.out.println("EXCEPTION WITH ADDING PARTICIPANT");
                            }
                        }
                    }
                    else if (i == 2){
                        String json = objectMapper.writeValueAsString(objects.get(2));
                        List<Payment> payms = objectMapper.readValue(json, new TypeReference<List<Payment>>(){});
                        if (payms != null){
                            for (Payment parm : payms){
                                Payment newPay = expPay.addPayments(parm);
                            }
                        }
                    }
                    else if (i == 3){
                        String json = objectMapper.writeValueAsString(objects.get(3));
                        List<Expense> expenses = objectMapper.readValue(json, new TypeReference<List<Expense>>(){});
                        if (expenses != null){
                            for (Expense exp : expenses){
                                // System.out.println("EXPENSE" + exp.toString());
                                Participant pp = new Participant(newev, exp.getCreditor().getName(),
                                        exp.getCreditor().getEmail(), exp.getCreditor().getIban(), exp.getCreditor().getBic(), exp.getCreditor().getId());
                                Expense ep = new Expense(newev, pp, exp.getAmount(), exp.getDate(), exp.getTitle(), exp.getTag(), exp.getCur());
                                // System.out.println("UPDATED EXPENSE" + ep);
                                try {
                                    expServer.addExpense(ep);
                                }
                                catch (Exception e) {
                                    System.out.println("EXCEPTION WITH EXPENSE");
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception e){
                throw new IOException();
                // System.out.println("SOMETHING WRONG WHILE PRINTING");
            }

            JOptionPane.showOptionDialog(null, h.get("key140"),h.get("key141"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{}, null);
//            imgMessage.setImage(new Image("images/notifications/Slide5.png"));
//            PauseTransition pause = new PauseTransition(Duration.seconds(6));
//            pause.setOnFinished(p -> imgMessage.setImage(null));
//            pause.play();
//            refresh();
            return;
        }
        catch (Exception e){
            JOptionPane.showOptionDialog(null, h.get("key131"),h.get("key132"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, new Object[]{}, null);
//            imgMessage.setImage(new Image("images/notifications/Slide4.png"));
//            PauseTransition pause = new PauseTransition(Duration.seconds(6));
//            pause.setOnFinished(p -> imgMessage.setImage(null));
//            pause.play();
            return;
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
            title3Text.setText(h.get("key104"));
            colName.setText(h.get("key31"));
            colDate1.setText(h.get("key100"));
            colDate2.setText(h.get("key101"));
            inputid.setPromptText(h.get("key94"));
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
        mc.help(h);
    }

}
