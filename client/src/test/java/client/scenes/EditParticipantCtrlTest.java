package client.scenes;

import client.utils.*;
import commons.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class EditParticipantCtrlTest {
    @Mock
    private EventServerUtils server;
    @Mock
    private MainCtrl mc;
    @Mock
    private ReadJSON jsonReader;
    @Mock
    private ParticipantsServerUtil partServer;
    @InjectMocks
    private EditParticipantCtrl editParticipantCtrl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        editParticipantCtrl = new EditParticipantCtrl(server, mc, jsonReader, partServer);
    }

    @Test
    void validate() {
        String name = "John Doe";
        String email = "John@email.com";
        String iban = "NL91ABNA0417164300";
        String bic = "ABNANL2A";
        assertTrue(editParticipantCtrl.validate(name, email, iban, bic));
        assertFalse(editParticipantCtrl.validate(name, "wrong.email", iban, bic));

        assertFalse(editParticipantCtrl.validate("", email, iban, bic));
        assertFalse(editParticipantCtrl.validate(name, "", iban, bic));
        assertFalse(editParticipantCtrl.validate(name, email, "", bic));
        assertFalse(editParticipantCtrl.validate(name, email, iban, ""));
        assertFalse(editParticipantCtrl.validate(name, "wrong@email", iban, bic));
        assertFalse(editParticipantCtrl.validate(name, email, "06565", bic));
    }

    @AfterEach
    void tearDown() {
    }
}