package client.scenes;

import client.utils.EventServerUtils;
// import client.utils.ServerUtils;
import commons.Event;
import jakarta.inject.Inject;
// import javafx.beans.property.SimpleLongProperty;
// import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
// import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
// import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
// import java.text.SimpleDateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
// import java.util.Date;
import java.util.ResourceBundle;

public class AdminDashboardCtrl implements Initializable {

    private final MainCtrl mc;
    private final EventServerUtils server;

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

    private ObservableList<Event> data;

    /**
     * Constructor for the AdminDashboardCtrl
     * @param mc the main controller
     * @param server server
     */
    @Inject
    public AdminDashboardCtrl(EventServerUtils server, MainCtrl mc) {
        this.mc = mc;
        this.server = server;
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
    }

    /**
     * Used to get back to the Start Screen
     * @throws IOException if something went wrong with showing the start screen
     */
    public void clickStart() throws IOException {
        mc.showStart();
    }

    /**
     * Goes to a specific event page
     * @throws IOException
     */
    public void clickEvent() throws IOException {
        String eid = inputid.getText();
        // if statement to check if the event does exist
        mc.showAdminEvent(eid);
    }

    /**
     * Imports a json file and adds it
     * @throws IOException
     */
    public void clickImport() throws IOException {
        String file = inputimport.getText();
    }

    /**
     * javadoc
     */
    public void refresh(){
        var events = server.getAllEvents();
        data = FXCollections.observableList(events);
        table.setItems(data);
    }
}
