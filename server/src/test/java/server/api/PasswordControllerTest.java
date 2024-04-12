package server.api;

import commons.Event;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class PasswordControllerTest {

    @Test
    void generateStrongPassword() {
        PasswordController passwordController = new PasswordController();
        var res = passwordController.generateStrongPassword();
        assertNotNull(res);
        assertEquals(8, res.length());
        assertTrue(res.matches(".*[a-z].*"));
    }

    @Test
    void checkPassword() {
        PasswordController passwordController = new PasswordController();
        assertFalse(passwordController.checkPassword("wrong"));
    }

    @Test
    void check() {
        PasswordController passwordController = new PasswordController();
        ResponseEntity<Boolean> res = passwordController.check("12345678");
        assertEquals(res, ResponseEntity.ok(false));
    }
}