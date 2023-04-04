package client.utils;

import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WebSocketStompStub extends WebSocketStompClient {
    /**
     * Class constructor. Sets {@link #setDefaultHeartbeat} to "0,0" but will
     * reset it back to the preferred "10000,10000" when a
     * {@link #setTaskScheduler} is configured.
     *
     * @param webSocketClient the WebSocket client to connect with
     */
    public WebSocketStompStub(WebSocketClient webSocketClient) {
        super(webSocketClient);
    }

    @Override
    public ListenableFuture<StompSession> connect(String url,
                                                  StompSessionHandler handler,
                                                  Object... uriVars) {
        return new ListenableFuture<StompSession>() {
            @Override
            public void addCallback(ListenableFutureCallback<? super StompSession> callback) {

            }

            @Override
            public void addCallback(SuccessCallback<? super StompSession> successCallback,
                                    FailureCallback failureCallback) {

            }

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public StompSession get()
                throws InterruptedException, ExecutionException {
                return new StompSessionStub();
            }

            @Override
            public StompSession get(long timeout, TimeUnit unit)
                throws InterruptedException, ExecutionException, TimeoutException
            {
                return null;
            }
        };
    }
}
