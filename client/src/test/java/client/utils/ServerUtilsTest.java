package client.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerUtilsTest {

    static WebSocketStompStub webSocketStompStub = new WebSocketStompStub(new StandardWebSocketClient());
    static ServerUtils sut = new ServerUtils(webSocketStompStub);

    @BeforeAll
    static void setup() {
        try {
            sut.setServer("localhost:8080");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void setServerTest() {
        assertEquals(sut.getServer(), "localhost:8080");
        assertEquals(sut.getHttpUrl(), "http://localhost:8080");
    }

    @Test
    void sendWebsocketMessageTest() {
        Object testObj = new Object();
        sut.send("/app/test/", testObj);
        assertEquals(ServerUtils.session.getSessionId(), "dest:/app/test/|Object:"+testObj); //hijacked function to return the last command
    }
}
