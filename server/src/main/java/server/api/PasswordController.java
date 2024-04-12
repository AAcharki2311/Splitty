package server.api;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

// UserController.java (Controller handling password generation)
@RestController
@RequestMapping("/api")
public class PasswordController {

    private static final Logger logger = Logger.getLogger(PasswordController.class.getName());

    /**
     * Generates a password and logs it to the server
     * @return a string password
     */
    @PostMapping("/generate-password")
    public ResponseEntity<String> generatePassword() {
        String generatedPassword = RandomStringUtils.random(8, true, true); // Your password generation logic here

        // Log the generated password
        logger.info("Generated admin password: " + generatedPassword);
        return ResponseEntity.ok(generatedPassword);
    }
}

