package com.example.testwebsockets.channel;

import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentLinkedDeque;

public class GroupChannel {

    private String title;

    public GroupChannel(String title) {
        this.title = title;
    }

    private ConcurrentLinkedDeque<WebSocketSession> sessions = new ConcurrentLinkedDeque<>();

    @PostConstruct
    public void init() {
        sessions.clear();
    }

    public boolean subscribe(WebSocketSession session) {
        return !sessions.contains(session) ? sessions.add(session) : false;
    }

    public boolean unsubscribe(WebSocketSession session) {
        return sessions.remove(session);
    }

    public int getOnline() {
        return sessions.size();
    }

    public ConcurrentLinkedDeque<WebSocketSession> getSessions() {
        return sessions;
    }
}
