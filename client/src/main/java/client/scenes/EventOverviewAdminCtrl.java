package client.scenes;

import client.utils.EventServerUtils;
import com.google.inject.Inject;
import commons.Event;
//import javafx.beans.property.SimpleStringProperty;
import commons.Expense;
// import javafx.collections.FXCollections;
import javafx.fxml.FXML;
// import javafx.fxml.Initializable;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
// import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;


public class EventOverviewAdminCtrl implements Initializable {

    /** BASIS **/
    private final EventServerUtils server;
    private final MainCtrl mc;
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
    private long eventid;
    @FXML
    private Text eventname;
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
    /** TABLE PARTICIPANTS **/
    @FXML
    private TableView<Expense> tablePart;
    @FXML
    private TableColumn<Expense, String> colName;
    @FXML
    private TableColumn<Expense, String> colEmail;

    /**
     * Constructer for the AdminEvent Controller
     * @param m the main controller
     * @param server the server
     */
    @Inject
    public EventOverviewAdminCtrl(EventServerUtils server, MainCtrl m) {
        this.mc = m;
        this.server = server;
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
//        colDate.setCellValueFactory(q -> new SimpleStringProperty(String.valueOf(q.getValue().)));
//        colId.setCellValueFactory(q -> new SimpleStringProperty(String.valueOf(q.getValue().id)));
//        colName.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().name));
//        colDate1.setCellValueFactory(q -> new SimpleStringProperty(formatter.format(q.getValue().creationDate)));
//        colDate2.setCellValueFactory(q -> new SimpleStringProperty(formatter.format(q.getValue().lastActDate)));
//        refresh();
        imgSet.setImage(new Image("images/settings.png"));
        imgArrow.setImage(new Image("images/arrow.png"));
        imgHome.setImage(new Image("images/home.png"));
        imageviewFlag.setImage(new Image("images/br-circle-01.png"));
    }

    /**
     * Goes to a specific event page
     * @throws IOException
     */
    public void clickDashboard() throws IOException {
        mc.showAdminDashboard();
    }

    /**
     * Imports a json file and adds it
     * @throws IOException
     */
    public void clickDelete() throws IOException {
        // delete event from the database
        Event x = server.getEventByID(eventid);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + x.name + "?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            //do stuff
            server.deleteEventByID(eventid);
            mc.showAdminDashboard();
        }
    }

    /**
     * javadoc
     * @param id id
     */
    public void update(String id) {
        long eid = Long.parseLong(id);
        this.eventid = eid;
        Event x = server.getEventByID(eid);
        // get the information from the database
        eventname.setText(x.getName());
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
     * javadoc
     */
    public void refresh(){
//        var expenses = server.getAllEvents();
//        data = FXCollections.observableList(events);
//        table.setItems(data);
    }
}