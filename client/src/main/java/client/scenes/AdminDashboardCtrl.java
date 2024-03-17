package client.scenes;

import client.utils.EventServerUtils;
// import client.utils.ServerUtils;
import client.utils.ReadJSON;
import client.utils.languageSwitchInterface;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
// import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
// import java.text.SimpleDateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
// import java.util.Date;
import java.util.*;

public class AdminDashboardCtrl implements Initializable, languageSwitchInterface {

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
    /** NEEDED FOR LANGUAGE SWITCH **/
    private final ReadJSON jsonReader;
    private List<String> languages = new ArrayList<>(Arrays.asList("Dutch", "English", "French"));
    @FXML
    private ImageView imageviewFlag;
    @FXML
    private ComboBox comboboxLanguage;
    /** PAGE **/
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
     * @param jsonReader jsonreader
     */
    @Inject
    public AdminDashboardCtrl(EventServerUtils server, MainCtrl mc, ReadJSON jsonReader) {
        this.mc = mc;
        this.jsonReader = jsonReader;
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

        comboboxLanguage.getItems().addAll(languages);
        comboboxLanguage.setOnAction(event -> {
            languageChange(comboboxLanguage);
            comboboxLanguage.setPromptText("Current language: " + comboboxLanguage.getSelectionModel().getSelectedItem());

            try {
                mc.ltest();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            mc.showAdminDashboard();
        });
        Image image = new Image("images/logo-no-background.png");
        imageview.setImage(image);

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

    /**
     * Javadoc
     * @param taal the language that the user wants to switch to
     * @throws NullPointerException
     */
    @Override
    public void langueageswitch(String taal) throws NullPointerException{
        String langfile = "language" + taal + ".json";
        HashMap<String, Object> h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        comboboxLanguage.setPromptText("Current language: " + taal);
//        welcometext.setText(h.get("key1").toString());
//        pleasetext.setText(h.get("key2").toString());
//        joinBTN.setText(h.get("key3").toString());
//        createBTN.setText(h.get("key4").toString());
//        loginBTN.setText(h.get("key5").toString());
//        recentviewedtext.setText(h.get("key6").toString());
        Image imageFlag = new Image(h.get("key0").toString());
        imageviewFlag.setImage(imageFlag);
    }
}
