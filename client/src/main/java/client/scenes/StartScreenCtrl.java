package client.scenes;

import client.utils.EventServerUtils;
import client.utils.LanguageSwitchInterface;
import client.utils.WriteEventNamesInterface;
import commons.Event;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import client.utils.ReadJSON;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;

public class StartScreenCtrl implements Initializable, LanguageSwitchInterface, WriteEventNamesInterface {
    @FXML
    private ComboBox comboboxLanguage;
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
    /** PAGE **/
    @FXML
    private ImageView imageview;
    @FXML
    private Text welcometext;
    @FXML
    private Text pleasetext;
    @FXML
    private Text recentviewedtext;
    @FXML
    private Button joinBTN;
    @FXML
    private Button createBTN;
    @FXML
    private Button loginBTN;
    @FXML
    private TextField eventName;
    @FXML
    private TextField eventJoin;
    @FXML
    private Text message;
    @FXML
    private Label recentEventLabel;

    /**
     * This method sets the image for the imageview and adds the items to the comboboxes
     * @param url represent the URL
     * @param resourceBundle represent the ResourceBundle
     * When pressed on the combobox of languages, it translates it immeditalty
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> eventNames = readEventsFromJson();
        if(eventNames.size() > 0){
            String text = "";
            List<String> tempList = eventNames.reversed();
            for(String element : tempList){
                text = text + element + "\n\n";
            }
            recentEventLabel.setText(text);
        } else{
            recentEventLabel.setText("No Recent Events");
        }
        comboboxLanguage.getItems().addAll(languages);
        comboboxLanguage.setOnAction(event -> {
            languageChange(comboboxLanguage);
            comboboxLanguage.setPromptText("Current language: " + comboboxLanguage.getSelectionModel().getSelectedItem());

            try {
                mc.ltest();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            mc.showStart();
        });
        Image image = new Image("images/logo-no-background.png");
        imageview.setImage(image);

        imgSet.setImage(new Image("images/settings.png"));
        imgArrow.setImage(new Image("images/arrow.png"));
        imgHome.setImage(new Image("images/home.png"));
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language that the user wants to switch to
     */
    @Override
    public void langueageswitch(String taal) throws NullPointerException{
        String langfile = "language" + taal + ".json";
        HashMap<String, Object> h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        comboboxLanguage.setPromptText("Current language: " + taal);
        welcometext.setText(h.get("key1").toString());
        pleasetext.setText(h.get("key2").toString());
        joinBTN.setText(h.get("key3").toString());
        createBTN.setText(h.get("key4").toString());
        loginBTN.setText(h.get("key5").toString());
        recentviewedtext.setText(h.get("key6").toString());
        Image imageFlag = new Image(h.get("key0").toString());
        imageviewFlag.setImage(imageFlag);
    }


    /**
     * Constructor of the StartScreenCtrl
     * @param mc represent the MainCtrl
     * @param jsonReader is an instance of the ReadJSON class, so it can read JSONS
     * @param server server
     */
    @Inject
    public StartScreenCtrl(EventServerUtils server, MainCtrl mc, ReadJSON jsonReader) {
        this.mc = mc;
        this.jsonReader = jsonReader;
        this.server = server;
    }

    /**
     * Method of the create event button, when pressed, it shows the eventoverview screen
     */
    public void createEvent() {
        try {
            String name = eventName.getText();
            Event test = new Event(name);
            test = server.addEvent(test);
            System.out.println("Event created: " + test.id + " " + test.name);
            writeEventName(test.name);
            mc.showEventOverview(String.valueOf(test.id));
        }
        catch (Exception e){
            message.setText("Name cannot be empty");
        }
    }

    /**
     * Method of the cancel button, when pressed, it shows the eventoverview screen
     */
    public void openEvent() {
        try {
            String eid = eventJoin.getText();
            if(checkNumber(eid) && checkIdExists(eid)){
                mc.showEventOverview(eid);
                writeEventName(server.getEventByID(Long.parseLong(eid)).getName());
                message.setText("");
            } else{
                throw new Exception("Exception message");
            }
        }
        catch (Exception e){
            message.setText("This Id is incorrect");
        }
    }

    /**
     * Method to check if the input is a number
     * @param eid the input
     * @return true if the input is a number, false if it isn't
     */
    public boolean checkNumber(String eid) {
        boolean check = false;
        try {
            if(!eid.isBlank() &&  Integer.parseInt(eid) >= 0){
                check = true;
            }
            return check;
        } catch (NumberFormatException e) {
            return check;
        }
    }

    /**
     * Method to check if the id exists
     * @param id the id of the event
     * @return true if the id exists, false if it doesn't
     */
    public boolean checkIdExists(String id) {
        //TODO: Implement this method that checks the database if the id is existing
        return true;
    }

    /**
     * Method of the show admin button, when pressed, it shows the showAdminLogin screen
     */
    public void clickAdmin() {
        mc.showAdminLogin();
    }

    /**
     * Javadoc
     */
    public void clickBack(){
        // does nothing as Start Screen has no back
    }

    /**
     * Javadoc
     */
    public void clickHome(){
        mc.showStart();
    }

    /**
     * Javadoc
     */
    public void clickSettings(){
        // mc.showSettings();
        mc.showAdminLogin();
    }

    /**
     * Method of the download template text, when pressed, it should download the template file
     */
    public void downloadTemplate() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("languageTemplate.json");
            fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getDefaultDirectory());
            fileChooser.setTitle("Splitty23 - Download Language Template");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
            File file = fileChooser.showSaveDialog(null);
            file.createNewFile();

            try(PrintWriter printWriter = new PrintWriter(file)){
                printWriter.print(readFile("src/main/resources/languageJSONS/languageTemplate.json"));
            }

            System.out.println("Download Complete...");

        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            String error = "Please restart the application and try again later.";
            errorAlert.setHeaderText("Error while downloading file.");
            errorAlert.setContentText(error);
            errorAlert.showAndWait();

            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to read a file
     * @param url the url of the file
     * @return the text of the file
     * @throws IOException if something is wrong with the file
     */
    public String readFile(String url) throws IOException{
        String text = "";
        Scanner myScanner = new Scanner(new File(url));
        while(myScanner.hasNextLine()){
            String data = myScanner.nextLine();
            text += data + "\n" ;
        }
        myScanner.close();
        return text;
    }

    /**
     * Method to reset the recent event label
     */
    public void reset() {
        List<String> eventNames = readEventsFromJson();
        if(eventNames.size() > 0){
            String text = "";
            List<String> tempList = eventNames.reversed();
            for(String element : tempList){
                text = text + element + "\n\n";
            }
            recentEventLabel.setText(text);
        } else{
            recentEventLabel.setText("No Recent Events");
        }
    }

}