package server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    void getRandom() {
        Config c = new Config();
        assertNotEquals(c.getRandom(), c.getRandom());
    }
}