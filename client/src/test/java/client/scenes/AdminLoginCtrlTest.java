package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdminLoginCtrlTest extends ApplicationTest {

    @Mock
    private EventServerUtils server;
    @Mock
    private PasswordServerUtils pwserver;
    @Mock
    private MainCtrl mc;
    @Mock
    private ReadJSON jsonReader;
    @Mock
    private WriteEventNames writeEventNames;
    @Mock
    private LanguageSwitch languageSwitch;
    @InjectMocks
    private AdminLoginCtrl alCtrl;

    private final ReadJSON jsonReader2 = new ReadJSON();
    private HashMap<String, String> h = jsonReader2.readJsonToMap("src/main/resources/languageJSONS/languageEN.json");
    private static final String TEST_CONFIG_PATH = "src/test/java/client/resources/readFile.txt";

    @BeforeAll
    static void setAllUp() {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
    }

    @Override
    public void start(Stage stage) throws Exception {
        Injector injector = Guice.createInjector(new MyModule());
        MyFXML fxml = new MyFXML(injector);

        Pair<AdminLoginCtrl, Parent> screen = fxml.load(AdminLoginCtrl.class,
                "client", "scenes", "AdminLogin.fxml");

        this.alCtrl = screen.getKey();
        this.alCtrl.setHashmap(h);
        MockitoAnnotations.openMocks(this).close();

        Scene scene = new Scene(screen.getValue());
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        alCtrl = new AdminLoginCtrl(server, pwserver, mc, jsonReader);
        this.alCtrl.setHashmap(h);
    }

    @Test
    void invalidLanguage(){
        AdminLoginCtrl al = new AdminLoginCtrl(null, null, null, null);
        assertThrows(Exception.class, () -> alCtrl.langueageswitch("x"));
    }


//    @Test
//    void testSetAndGetHashMap() {
//        HashMap<String, String> hash = new HashMap<>();
//        hash.put("key", "value");
//        alCtrl.setHashmap(hash);
//        assertEquals(hash, alCtrl.getHashmap());
//    }

    @AfterEach
    void tearDown() {
    }
}