package client.scenes;

import client.utils.EventServerUtils;
import commons.Event;
import jakarta.inject.Inject;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdminDashboardCtrl implements Initializable {

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
    private Text output;
    @FXML
    private ImageView imageview;
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

        imageviewFlag.setImage(new Image("images/br-circle-01.png"));
        imageview.setImage(new Image("images/logo-no-background.png"));
        imgSet.setImage(new Image("images/settings.png"));
        imgArrow.setImage(new Image("images/arrow.png"));
        imgHome.setImage(new Image("images/home.png"));
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
