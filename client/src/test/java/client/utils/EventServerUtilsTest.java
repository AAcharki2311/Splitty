package client.utils;

import commons.Expense;
import commons.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServerUtilsTest {

    @Mock
    private StandardWebSocketClient client;

    @Mock
    private WebSocketStompClient stomp;

    @Mock
    private StompSession session;

    @Mock
    private ReadURL readURL;

    @InjectMocks
    private EventServerUtils ESU;

    @BeforeEach
    void setUp() {
        when(stomp.connectAsync(anyString(), any(StompSessionHandlerAdapter.class))).thenReturn(CompletableFuture.completedFuture(session));
    }

    @Test
    void testConnect() throws Exception {
        StompSession result = ESU.connect("ws://localhost:8080/websocket");
        assertEquals(session, result);
    }

    @Test
    void testInitiateWebsocketEventConnection() {
        when(readURL.readServerUrl(anyString())).thenReturn("http://localhost:8080");
        Consumer<Participant> participantConsumer = participant -> {};
        Consumer<Expense> expenseConsumer = expense -> {};
        ESU.initiateWebsocketEventConnection(1L, participantConsumer, expenseConsumer);
        verify(session, times(2)).subscribe(anyString(), any());
    }
}