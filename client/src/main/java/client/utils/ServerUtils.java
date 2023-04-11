/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.utils;

import commons.Board;
import commons.Card;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private static String server;
    private static String httpUrl;

    public static StompSession session;
    private StandardWebSocketClient client = new StandardWebSocketClient();
    private WebSocketStompClient stomp = new WebSocketStompClient(client);

    public ServerUtils() {
    }

    public ServerUtils(WebSocketStompClient stomp) {
        this.stomp = stomp;
    }

    public static String getServer() {
        return server;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setServer(String server) throws Exception {
        this.server = server;
        httpUrl = "http://" + server;
        session = connect("ws://" + server + "/websocket");
    }


    public List<commons.List> getLists() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(httpUrl).path("api/lists") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<>() {
                });
    }

    public List<Board> getBoards() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(httpUrl).path("api/boards") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<>() {
                });
    }

    public Board getBoardById(long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(httpUrl).path("api/boards/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<>() {
                });
    }


    public commons.Card replaceCard(commons.Card card) {
        Long id = card.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(httpUrl).path("api/cards/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(card, APPLICATION_JSON), commons.Card.class);
    }

    //establishes a STOMP message format websocket session
    public StompSession connect(String url) throws Exception {
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            return session = stomp.connect(url, new StompSessionHandlerAdapter() {
            }).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new Exception(e);
        }
        throw new IllegalStateException();
    }

    public <T> void registerForUpdatesSockets(
            String dest,
            Class<T> type,
            Consumer<T> consumer) {
        try {
            session.subscribe(dest, new StompFrameHandler() {
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return type;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    consumer.accept((T) payload);
                }

            });
        } catch (Exception e) {

        }
    }

    public void send(String dest, Object o) {
        session.send(dest, o);
    }

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();

    public void registerForUpdatesPolling(Consumer<Card> consumer) {
        EXEC.submit(() -> {
            while (!Thread.interrupted()) {
                try {
                    var res = ClientBuilder.newClient(new ClientConfig())
                            .target(httpUrl).path("api/cards/updates")
                            .request(APPLICATION_JSON)
                            .accept(APPLICATION_JSON)
                            .get(Response.class);

                    if (res == null) {
                        System.out.println("Response is null");
                        continue;
                    }
                    if (res.getStatus() == 204) {
                        System.out.println("TimeOut");
                    } else if (res.getStatus() == 200) {
                        System.out.println("change registered");
                        var c = res.readEntity(Card.class);
                        consumer.accept(c);
                    } else {
                        System.out.println("Other error");
                    }
                } catch (Exception e) {
                    System.out.println("Exception occurred: " + e.getMessage());
                }
            }
        });
    }

    public void stop() {
        EXEC.shutdownNow();
    }
}
