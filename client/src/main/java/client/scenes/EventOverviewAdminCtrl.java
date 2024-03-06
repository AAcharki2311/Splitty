package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
// import javafx.fxml.Initializable;
import javafx.scene.control.Label;
// import javafx.scene.text.Text;

import java.io.IOException;


public class EventOverviewAdminCtrl {
    private final MainCtrl mc;

    @FXML
    private Label eventname;

    /**
     * Constructer for the AdminEvent Controller
     * @param m the main controller
     */
    @Inject
    public EventOverviewAdminCtrl(MainCtrl m) {
        this.mc = m;
    }

    /**
     * Goes to a specific event page
     * @throws IOException
     */
    public void clickDashboard() throws IOException {
        mc.showAdminDashboard();
    }

    /**
     * Imports a json file and adds it
     * @throws IOException
     */
    public void clickDelete() throws IOException {
        // delete event from the database
    }

    /**
     * javadoc
     * @param id id
     */
    public void update(String id) {
        // get the information from the database
        eventname.setText(id);
    }
}