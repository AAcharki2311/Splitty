package client.scenes;

import jakarta.inject.Inject;

import java.io.IOException;

public class AdminDashboardCtrl {

    private final MainCtrl mc;

    @Inject
    public AdminDashboardCtrl(MainCtrl mc) {
        this.mc = mc;
    }

    public void clickStart() throws IOException {
        mc.showStart();
    }
}