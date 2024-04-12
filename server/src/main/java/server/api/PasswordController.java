package server.api;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordController {
    private final String password;

    /**
     * PasswordController constructor
     */
    public PasswordController( ) {
        password = generateStrongPassword();
        System.out.println(" *** Generated password: " + password + " ***");
    }

    /**
     * Generates a strong password
     * @return the generated password
     */
    public String generateStrongPassword() {
        return RandomStringUtils.random(8, true, true);
    }

    /**
     * Returns true if equal
     * @param userPassword the user input
     * @return true if equal
     */
    public boolean checkPassword(String userPassword) {
        return password.equals(userPassword);
    }

    /**
     * Checks if the password is correct
     * @param pw the password to check
     * @return true if the password is correct
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Boolean> check(@RequestBody String pw) {
        if (checkPassword(pw)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }
}

