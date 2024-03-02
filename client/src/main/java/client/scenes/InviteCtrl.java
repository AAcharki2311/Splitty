package client.scenes;

import jakarta.inject.Inject;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.fxml.FXML;

public class InviteCtrl  {

    private MainCtrl mc;

    private int inviteCode = 356;

    @FXML
    private Button copyButton;

    @Inject
    public InviteCtrl(MainCtrl mc) {
        this.mc = mc;
    }

    public void copyToClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(String.valueOf(inviteCode));
        clipboard.setContent(content);
    }

    public void pressed() {
        copyToClipboard();
        copyButton.setText("Copied");
    }

    public void clickBack() {
        mc.showEventOverview();
    }

}