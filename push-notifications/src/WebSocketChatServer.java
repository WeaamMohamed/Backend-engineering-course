package com.example.backend;

import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@ServerEndpoint("/chat")
public class WebSocketChatServer {

    private static final Set<Session> connections = Collections.synchronizedSet(new HashSet<>());
    private static final Logger logger = Logger.getLogger(WebSocketChatServer.class.getName());

    @OnOpen
    public void onOpen(Session session) {
        connections.add(session);
        broadcastMessage("User " + session.getId() + " just connected...");
        logger.info("New connection opened: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("Message received from " + session.getId() + ": " + message);
        broadcastMessage("User " + session.getId() + " says: " + message);
    }

    @OnClose
    public void onClose(Session session) {
        connections.remove(session);
        broadcastMessage("User " + session.getId() + " just disconnected...");
        logger.info("Connection closed: " + session.getId());
    }

    private static void broadcastMessage(String message) {
        synchronized (connections) {
            for (Session session : connections) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    logger.warning("Error sending message to session " + session.getId());
                }
            }
        }
    }
}
