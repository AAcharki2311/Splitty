package client.scenes;

import client.utils.EventServerUtils;
import com.google.inject.Inject;
import commons.Event;
import javafx.fxml.FXML;
// import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
// import javafx.scene.text.Text;

import java.io.IOException;


public class EventOverviewAdminCtrl {
    private final MainCtrl mc;
    private final EventServerUtils server;

    private long eventid;

    @FXML
    private Label eventname;

    /**
     * Constructer for the AdminEvent Controller
     * @param m the main controller
     * @param server the server
     */
    @Inject
    public EventOverviewAdminCtrl(EventServerUtils server, MainCtrl m) {
        this.mc = m;
        this.server = server;
        this.eventid = 0;
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
        Event x = server.getEventByID(eventid);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + x.name + "?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            //do stuff
            server.deleteEventByID(eventid);
            mc.showAdminDashboard();
        }
    }

    /**
     * javadoc
     * @param id id
     */
    public void update(String id) {
        long eid = Long.parseLong(id);
        this.eventid = eid;
        Event x = server.getEventByID(eid);
        // get the information from the database
        eventname.setText(x.getName());
    }
}