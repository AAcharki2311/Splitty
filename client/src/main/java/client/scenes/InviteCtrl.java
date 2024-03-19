package client.scenes;

import client.utils.EventServerUtils;
import client.utils.ReadJSON;
import client.utils.LanguageSwitchInterface;
import jakarta.inject.Inject;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class InviteCtrl implements Initializable, LanguageSwitchInterface {

    private MainCtrl mc;
    private final ReadJSON jsonReader;
    @FXML
    private ImageView imageview;
    private long inviteCode;
    @FXML
    private Label labelEventName;
    @FXML
    private Label inviteText;
    @FXML
    private Label orText;
    @FXML
    private Label inviteCodeText;
    @FXML
    private Label integerInviteCode;
    @FXML
    private Label addEmailText;
    @FXML
    private Button copyButton;
    @FXML
    private Button sendButton;
    @FXML
    private Button cancelBtn;
    private final EventServerUtils server;
    private long eventid;

    /**
     * Constructor of the InviteCtrl
     * @param server represent the EventServerUtils
     * @param mc represent the MainCtrl
     * @param jsonReader represent the ReadJSON
     */
    @Inject
    public InviteCtrl(EventServerUtils server, MainCtrl mc, ReadJSON jsonReader) {
        this.mc = mc;
        this.jsonReader = jsonReader;
        this.server = server;
    }

    /**
     * This method sets the images for the imageviews and adds the items to the comboboxes
     * @param url represent the URL
     * @param resourceBundle represent the ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("images/logo-no-background.png");
        imageview.setImage(image);

        Image imageBtnCopy = new Image("images/copy-regular.png");
        ImageView imageviewCopy = new ImageView(imageBtnCopy);
        imageviewCopy.setFitHeight(13);
        imageviewCopy.setFitWidth(13);
        copyButton.setGraphic(imageviewCopy);

        Image imageBtnSend = new Image("images/paper-plane-regular.png");
        ImageView imageviewSend = new ImageView(imageBtnSend);
        imageviewSend.setFitHeight(13);
        imageviewSend.setFitWidth(13);
        sendButton.setGraphic(imageviewSend);

    }

    /**
     * This method copies the invite code to the clipboard
     */
    public void copyToClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(integerInviteCode.getText());
        clipboard.setContent(content);
    }

    /**
     * This method changes the image in the copybutton when it is pressed
     */
    public void pressedCopy() {
        copyToClipboard();
        Image imageBtnCopy = new Image("images/copy-solid.png");
        ImageView imageviewCopy = new ImageView(imageBtnCopy);
        imageviewCopy.setFitHeight(13);
        imageviewCopy.setFitWidth(13);
        copyButton.setGraphic(imageviewCopy);
    }

    /**
     * This method changes the image in the sendbutton when it is pressed
     */
    public void pressedSend() {
        Image imageBtnSend = new Image("images/paper-plane-solid.png");
        ImageView imageviewSend = new ImageView(imageBtnSend);
        imageviewSend.setFitHeight(13);
        imageviewSend.setFitWidth(13);
        sendButton.setGraphic(imageviewSend);
    }

    /**
     * Method of the cancel button, when pressed, it shows the eventoverview screen
     */
    public void clickBack() throws IOException {
        mc.showEventOverview(String.valueOf(eventid));
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language that the user wants to switch to
     */
    @Override
    public void langueageswitch(String taal) {
        String langfile = "language" + taal + ".json";
        HashMap<String, Object> h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        inviteText.setText(h.get("key33").toString());
        orText.setText(h.get("key34").toString());
        inviteCodeText.setText(h.get("key35").toString());
        addEmailText.setText(h.get("key36").toString());
        cancelBtn.setText(h.get("key26").toString());
    }

    /**
     * Updates the page with the right information
     * @param id the id of the event
     */
    public void update(String id){
        long eid = Long.parseLong(id);
        this.eventid = eid;
        System.out.println("reached");
        System.out.println(eid + " " + server.getEventByID(eid).getName());

        labelEventName.setText(server.getEventByID(eid).getName());

        this.inviteCode = eventid;
        long stringEventIdU = inviteCode;
        String stringInviteCode = String.valueOf(stringEventIdU);
        switch (stringInviteCode.length()){
            case 1:
                integerInviteCode.setText("00000" + stringInviteCode);
                break;
            case 2:
                integerInviteCode.setText("0000" + stringInviteCode);
                break;
            case 3:
                integerInviteCode.setText("000" + stringInviteCode);
                break;
            case 4:
                integerInviteCode.setText("00" + stringInviteCode);
                break;
            case 5:
                integerInviteCode.setText("0" + stringInviteCode);
                break;
            default:
                integerInviteCode.setText(stringInviteCode);
                break;
        }
    }
}