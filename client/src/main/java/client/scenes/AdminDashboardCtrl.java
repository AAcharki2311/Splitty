package client.scenes;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
// import javafx.scene.control.Label;
import javafx.scene.control.TextField;
// import org.w3c.dom.Text;

import java.io.IOException;

public class AdminDashboardCtrl {

    private final MainCtrl mc;

    @FXML
    private TextField inputname;
    @FXML
    private TextField inputimport;

    /**
     * Constructor for the AdminDashboardCtrl
     * @param mc the main controller
     */
    @Inject
    public AdminDashboardCtrl(MainCtrl mc) {
        this.mc = mc;
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
        String name = inputname.getText();
        // if statement to check if the event does exist
        mc.showAdminEvent();
    }

    /**
     * Imports a json file and adds it
     * @throws IOException
     */
    public void clickImport() throws IOException {
        String file = inputimport.getText();
    }
}
