package client.scenes;

import client.utils.*;
import commons.Event;
import commons.Participant;
import jakarta.inject.Inject;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;

public class StartScreenCtrl implements Initializable {
    @FXML
    private ComboBox comboboxLanguage;
    private final EventServerUtils server;
    private final ParticipantsServerUtil partServer;
    private final MainCtrl mc;
    private HashMap<String, String> h;
    /** MENU **/
    @FXML
    private ImageView imgSet;
    @FXML
    private ImageView imgArrow;
    @FXML
    private ImageView imgHome;
    /** NEEDED FOR LANGUAGE SWITCH **/
    private List<String> languages = new ArrayList<>(Arrays.asList("Dutch", "English", "French"));
    @FXML
    private ImageView imageviewFlag;
    /** PAGE **/
    private final ReadJSON jsonReader;
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
    @FXML
    private Button addUserInfoBtn;
    @FXML
    private ImageView warningImageview;
    private Participant userParticipant;
    private WriteEventNames writeEventNames;
    private LanguageSwitch languageSwitch;

    /**
     * Constructor of the StartScreenCtrl
     * @param mc represent the MainCtrl
     * @param jsonReader is an instance of the ReadJSON class, so it can read JSONS
     * @param server server
     * @param partServer participant server
     * @param writeEventNames the WriteEventNames class
     * @param languageSwitch the LanguageSwitch class
     */
    @Inject
    public StartScreenCtrl(EventServerUtils server, ParticipantsServerUtil partServer,
                           MainCtrl mc, ReadJSON jsonReader,
                           WriteEventNames writeEventNames, LanguageSwitch languageSwitch) {
        this.partServer = partServer;
        this.mc = mc;
        this.jsonReader = jsonReader;
        this.server = server;
        this.writeEventNames = writeEventNames;
        this.languageSwitch = languageSwitch;
    }

    /**
     * This method sets the image for the imageview and adds the items to the comboboxes
     * @param url represent the URL
     * @param resourceBundle represent the ResourceBundle
     * When pressed on the combobox of languages, it translates it immeditalty
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> eventNames = writeEventNames.readEventsFromJson("src/main/resources/recentEvents.json");
        if(!eventNames.isEmpty()){
            String text = "";
            List<String> tempList = eventNames.reversed();
            for(String element : tempList) text = text + element + "\n\n";
            recentEventLabel.setText(text);
        } else{
            recentEventLabel.setText(h.get("key52"));
        }
        comboboxLanguage.getItems().addAll(languages);
        comboboxLanguage.setOnAction(event -> {
            String path = "src/main/resources/configfile.properties";
            String language = comboboxLanguage.getValue().toString();
            languageSwitch.languageChange(path, language);
            comboboxLanguage.setPromptText(h.get("key53") + comboboxLanguage.getSelectionModel().getSelectedItem());

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
    public void langueageswitch(String taal) throws NullPointerException{
        String langfile = "language" + taal + ".json";
        h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        comboboxLanguage.setPromptText(h.get("key53") + taal);
        welcometext.setText(h.get("key1"));
        pleasetext.setText(h.get("key2"));
        joinBTN.setText(h.get("key3"));
        createBTN.setText(h.get("key4"));
        loginBTN.setText(h.get("key5"));
        recentviewedtext.setText(h.get("key6"));
        addUserInfoBtn.setText(h.get("key14"));
        Image imageFlag = new Image(h.get("key0"));
        imageviewFlag.setImage(imageFlag);
    }

    /**
     * Method of the create event button, when pressed, it shows the eventoverview screen
     */
    public void createEvent() {
        try {
            String name = eventName.getText();
            if(name.isBlank()){
                throw new IllegalArgumentException(h.get("key54"));
            } else{
                List<Event> allEvents = server.getAllEvents();
                List<String> namesOfAllEvents = new ArrayList<>();
                for(Event e : allEvents){
                    namesOfAllEvents.add(e.getName());
                }
                if(!namesOfAllEvents.contains(name)){
                    Event newEvent = new Event(name);
                    newEvent = server.addEvent(newEvent);
                    String filepath = "src/main/resources/recentEvents.json";
                    writeEventNames.writeEventName(filepath, ("name: " + newEvent.name + " - id: " + (newEvent.id)), String.valueOf(newEvent.id));

                    if (userParticipant != null){
                        int choice = JOptionPane.showOptionDialog(null, h.get("key55"),
                                h.get("key56"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
                                new String[]{h.get("key57"), h.get("key58")}, "default");
                        if(choice == JOptionPane.OK_OPTION){
                            userParticipant.setEvent(newEvent);
                            partServer.addParticipant(userParticipant);
                        }
                    }
                    mc.showEventOverview(String.valueOf(newEvent.id));
                } else {
                    throw new IllegalArgumentException(h.get("key59"));
                }
            }
        }
        catch (Exception e){
            message.setText(e.getMessage());
        }
    }

    /**
     * Method of the Join button, when pressed, it shows the eventoverview screen
     */
    public void openEvent() {
        try {
            String eid = eventJoin.getText();
            if(checkNumber(eid)){
                try{
                    server.getEventByID(Long.parseLong(eid));
                    mc.showEventOverview(eid);
                    String filepath = "src/main/resources/recentEvents.json";
                    writeEventNames.writeEventName(filepath, ("name: " + server.getEventByID(Long.parseLong(eid)).getName() + " - id: " + eid), eid);
                    message.setText("");
                } catch(Exception e){
                    throw new IllegalArgumentException(h.get("key60"));
                }
            } else{
                throw new IllegalArgumentException(h.get("key61"));
            }
        }
        catch (Exception e){
            message.setText(e.getMessage());
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
     * Method of the show admin button, when pressed, it shows the showAdminLogin screen
     */
    public void clickAdmin() {
        mc.showAdminLogin();
    }

    /**
     * Method of the back button, when pressed, it shows the start screen
     */
    public void clickBack(){
        // does nothing as Start Screen has no back
    }

    /**
     * Method of the home button, when pressed, it shows the start screen
     */
    public void clickHome(){
        mc.showStart();
    }

    /**
     * Method of the settings button, when pressed, it shows the *settings screen*
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

            warningImageview.setImage(new Image("images/notifications/Slide5.png"));
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), warningImageview);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();

        } catch (Exception e) {
            warningImageview.setImage(new Image("images/notifications/Slide4.png"));
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), warningImageview);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();

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
     * Shows the popup screen
     */
    public void showPopup() {
        while(true){
            JTextField textFieldName = new JTextField();
            JTextField textFieldEmail = new JTextField();
            JTextField textFieldIBAN = new JTextField();
            JTextField textFieldBIC = new JTextField();
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel(h.get("key62")));
            panel.add(new JLabel(h.get("key31")));
            panel.add(textFieldName);
            panel.add(new JLabel("Email: "));
            panel.add(textFieldEmail);
            panel.add(new JLabel("IBAN: "));
            panel.add(textFieldIBAN);
            panel.add(new JLabel("BIC: "));
            panel.add(textFieldBIC);
            Object[] options = {"OK", "Skip"};

            int result = JOptionPane.showOptionDialog(null, panel, h.get("key63"),
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (result == JOptionPane.OK_OPTION) {
                String name = textFieldName.getText();
                String email = textFieldEmail.getText();
                String iban = textFieldIBAN.getText();
                String bic = textFieldBIC.getText();
                if(name.isBlank() || email.isBlank() || iban.isBlank() || bic.isBlank() ||
                        !email.matches(".*@.+\\..+")){
                    JOptionPane.showMessageDialog(null, h.get("key64"));
                } else{
                    this.userParticipant = new Participant(null, name, email, iban, bic);
                    mc.setParticipant(userParticipant);
                    break;
                }
            } else {
                if(userParticipant != null) break;
                this.userParticipant = null;
                break;
            }
        }
    }

    /**
     * Method to reset the recent event label
     */
    public void reset() {
        List<String> eventNames = writeEventNames.readEventsFromJson("src/main/resources/recentEvents.json");
        if(!eventNames.isEmpty()){
            String text = "";
            List<String> tempList = eventNames.reversed();
            for(String element : tempList) text = text + element + "\n\n";
            recentEventLabel.setText(text);
        } else{
            recentEventLabel.setText(h.get("key52").toString());
        }
    }
}