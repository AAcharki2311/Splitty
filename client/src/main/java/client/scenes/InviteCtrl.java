package client.scenes;

import client.utils.languageSwitchInterface;
import jakarta.inject.Inject;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.fxml.FXML;

import java.io.IOException;

public class InviteCtrl implements languageSwitchInterface {

    private MainCtrl mc;

    private int inviteCode = 356;

    @FXML
    private Button copyButton;

    /**
     * Constructor of the InviteCtrl
     * @param mc represent the MainCtrl
     */
    @Inject
    public InviteCtrl(MainCtrl mc) {
        this.mc = mc;
    }

    /**
     * This method copies the invite code to the clipboard
     */
    public void copyToClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(String.valueOf(inviteCode));
        clipboard.setContent(content);
    }

    /**
     * This method changes the text of the copyButton to "Copied" when it is pressed
     */
    public void pressed() {
        copyToClipboard();
        copyButton.setText("Copied");
    }

    /**
     * Method of the cancel button, when pressed, it shows the eventoverview screen
     */
    public void clickBack() throws IOException {
        mc.showEventOverview();
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language that the user wants to switch to
     */
    @Override
    public void langueageswitch(String taal) {

    }
}