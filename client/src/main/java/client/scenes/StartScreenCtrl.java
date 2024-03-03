package client.scenes;

import jakarta.inject.Inject;

public class StartScreenCtrl {

    private final MainCtrl mc;
    @Inject
    public StartScreenCtrl(MainCtrl mc) {
        this.mc = mc;
    }

    public void clickEvent() {
        mc.showEventOverview();
    }

    public void clickAdmin() {
        mc.showAdminLogin();
    }
}