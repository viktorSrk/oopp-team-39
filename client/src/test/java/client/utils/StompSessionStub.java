package client.utils;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

public class StompSessionStub implements StompSession {
    public String lastCommand;
    @Override
    public String getSessionId() {
        return lastCommand;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void setAutoReceipt(boolean enabled) {

    }

    @Override
    public Receiptable send(String destination, Object payload) {
        lastCommand="dest:"+destination+"|Object:"+payload;
        return null;
    }

    @Override
    public Receiptable send(StompHeaders headers, Object payload) {
        return null;
    }

    @Override
    public Subscription subscribe(String destination, StompFrameHandler handler) {
        return null;
    }

    @Override
    public Subscription subscribe(StompHeaders headers, StompFrameHandler handler) {
        return null;
    }

    @Override
    public Receiptable acknowledge(String messageId, boolean consumed) {
        return null;
    }

    @Override
    public Receiptable acknowledge(StompHeaders headers, boolean consumed) {
        return null;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public void disconnect(StompHeaders headers) {

    }
}
