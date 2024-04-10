package client.scenes;

import client.utils.*;
import commons.Event;
import commons.Participant;
import jakarta.inject.Inject;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import java.util.Timer;
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
    private ImageView imgHome;
    /** NEEDED FOR LANGUAGE SWITCH **/
    private List<String> languages = new ArrayList<>(Arrays.asList("Dutch", "English", "French"));
    @FXML
    private ImageView imageviewFlag;
    private String language;
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
    private Timer timer;

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
        this.language = "English";
    }

    /**
     * This method sets the image for the imageview and adds the items to the comboboxes
     * @param url represent the URL
     * @param resourceBundle represent the ResourceBundle
     * When pressed on the combobox of languages, it translates it immeditalty
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboboxLanguage.getItems().addAll(languages);
        comboboxLanguage.setOnAction(event -> {
            String path = "src/main/resources/configfile.properties";
            this.language = comboboxLanguage.getValue().toString();
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
        imgHome.setImage(new Image("images/home.png"));
    }

    /**
     * This method reads the recent events from the JSON file and sets the text of the recent events label
     */
    public void readRecentEvents() {
        List<String> eventNames = writeEventNames.readEventsFromJson("src/main/resources/recentEvents.json");
        if(!eventNames.isEmpty()){
            String text = "";
            List<String> tempList = eventNames.reversed();
            for(String element : tempList) text = text + "\n" + element + "\n";
            recentEventLabel.setText(text);
        } else{
            recentEventLabel.setText(h.get("key52"));
        }
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
        eventJoin.setPromptText(h.get("key94"));
        eventName.setPromptText(h.get("key95"));
    }

    /**
     * Method of the create event button, when pressed, it shows the eventoverview screen
     */
    public void createEvent() {
        try {
            String name = eventName.getText();
            if(name.isBlank()){
                warningImageview.setImage(new Image("images/notifications/Slide3.png"));
                PauseTransition pause = new PauseTransition(Duration.seconds(6));
                pause.setOnFinished(p -> warningImageview.setImage(null));
                pause.play();
                // throw new IllegalArgumentException(h.get("key54"));
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
                    writeEventNames.writeEventName(filepath, (newEvent.name + "\nID: " + (newEvent.id)), String.valueOf(newEvent.id));

                    if (userParticipant != null){
                        int choice = JOptionPane.showOptionDialog(null, h.get("key55"),
                                h.get("key56"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
                                new String[]{h.get("key57"), h.get("key58")}, "default");
                        if(choice == JOptionPane.OK_OPTION){
                            userParticipant.setEvent(newEvent);
                            partServer.addParticipant(userParticipant);
                        }
                    }

                    HashMap<String, String> ht = jsonReader.readJsonToMap("src/main/resources/tagcolors.json");
                    ht.put("Food?"+(String.valueOf(newEvent.id)), "#43CE43");
                    ht.put("Entrance Fees?"+(String.valueOf(newEvent.id)), "#616BD0");
                    ht.put("Travel?" + (String.valueOf(newEvent.id)), "#D71919");
                    jsonReader.writeMapToJsonFile(ht, "src/main/resources/tagcolors.json");

                    mc.showEventOverview(String.valueOf(newEvent.id));

                } else {
                    warningImageview.setImage(new Image("images/notifications/Slide2.png"));
                    PauseTransition pause = new PauseTransition(Duration.seconds(6));
                    pause.setOnFinished(p -> warningImageview.setImage(null));
                    pause.play();
                    throw new IllegalArgumentException(h.get("key59"));
                }
            }
        }
        catch (Exception e){
            message.setText(e.getMessage());
            warningImageview.setImage(new Image("images/notifications/Slide4.png"));
            PauseTransition pause = new PauseTransition(Duration.seconds(6));
            pause.setOnFinished(p -> warningImageview.setImage(null));
            pause.play();
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
                    long eventID = Long.parseLong(eid);
                    Event x = server.getEventByID(eventID);
                    x.setLastActDate(new Date());
                    server.addEvent(x);
                    mc.showEventOverview(eid);
                    String dest = "/topic/events/"+eid;
                    System.out.println("Destination: "+dest);
                    new Thread() {
                        /**
                         * This method is run by the thread when it executes. Subclasses of {@code
                         * Thread} may override this method.
                         *
                         * <p> This method is not intended to be invoked directly. If this thread is a
                         * platform thread created with a {@link Runnable} task then invoking this method
                         * will invoke the task's {@code run} method. If this thread is a virtual thread
                         * then invoking this method directly does nothing.
                         *
                         * @implSpec The default implementation executes the {@link Runnable} task that
                         * the {@code Thread} was created with. If the thread was created without a task
                         * then this method does nothing.
                         */
                        @Override
                        public void run() {
                            server.initiateWebsocketEventConnection(eventID, p -> {
                                System.out.println("[Websocket] Received: "+p);
                                if(p.getEvent().getId() == mc.getEventOCtrl().getCurrentEventID()) {
                                    System.out.println("[Websocket] ID's match thus adding...");
                                    mc.getEventOCtrl().putParticipant(p);
                                }
                            }, e -> {
                                System.out.println("[Websocket] Received: "+e);
                                if(e.getEvent().getId() == mc.getEventOCtrl().getCurrentEventID()) {
                                    System.out.println("[Websocket] ID's match thus adding...");
                                    mc.getEventOCtrl().putExpense(e);
                                }
                            });
                            mc.getEventOCtrl().getEventServerUtils().setWebsocketConnection(server.getWebsocketConnection());
                        }
                    }.start();

                    String filepath = "src/main/resources/recentEvents.json";
                    writeEventNames.writeEventName(filepath, (server.getEventByID(Long.parseLong(eid)).getName() + "\nID: " + eid), eid);
                    message.setText("");
                } catch(Exception e){
                    warningImageview.setImage(new Image("images/notifications/Slide1.png"));
                    PauseTransition pause = new PauseTransition(Duration.seconds(6));
                    pause.setOnFinished(p -> warningImageview.setImage(null));
                    pause.play();
                    throw new IllegalArgumentException(h.get("key60"));
                }
            } else{
                warningImageview.setImage(new Image("images/notifications/Slide2.png"));
                PauseTransition pause = new PauseTransition(Duration.seconds(6));
                pause.setOnFinished(p -> warningImageview.setImage(null));
                pause.play();
                throw new IllegalArgumentException(h.get("key61"));
            }
        }
        catch (Exception e){
            message.setText(e.getMessage());
            warningImageview.setImage(new Image("images/notifications/Slide4.png"));
            PauseTransition pause = new PauseTransition(Duration.seconds(6));
            pause.setOnFinished(p -> warningImageview.setImage(null));
            pause.play();
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
     * Method of the home button, when pressed, it shows the start screen
     */
    public void clickHome(){
        mc.showStart();
    }

    /**
     * Method of the settings button, when pressed, it shows the keyboard combo's
     */
    public void clickSettings(){
        mc.help();
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
     * Method to stop the timer
     * Since the user is not on the start screen anymore
     */
    public void stopTimer() {
        if(timer != null) timer.cancel();
    }

    /**
     * Method to start the timer
     * To update the recent events every 3 seconds
     */
    public void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    readRecentEvents();
                });
            }
        }, 0, 5000);
    }
}