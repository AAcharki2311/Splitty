package client.scenes;

import jakarta.inject.Inject;

public class AdminDashboardCtrl {

    private final MainCtrl mc;

    @Inject
    public AdminDashboardCtrl(MainCtrl mc) {
        this.mc = mc;
    }

    public void clickStart() {
        mc.showStart();
    }
}