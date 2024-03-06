package client.scenes;

import com.google.inject.Inject;

import java.io.IOException;


public class EventOverviewAdminCtrl {
    private final MainCtrl mc;

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
}