package client.scenes;

import jakarta.inject.Inject;

import java.io.IOException;

public class AdminDashboardCtrl {

    private final MainCtrl mc;

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
}