package client.scenes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminDashboardCtrlTest {

    @Test
    void checkEvent() {
        AdminDashboardCtrl adash = new AdminDashboardCtrl(null, null, null, null, null, null);
        assertFalse(adash.checkEvent(""));
        assertFalse(adash.checkEvent("x"));
    }

    @Test
    void showError(){
        AdminDashboardCtrl adash = new AdminDashboardCtrl(null, null, null, null, null, null);
        adash.showError2();
    }
}