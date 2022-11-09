package com.example.testwebsockets.channel;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class ChannelManager {

    private ConcurrentHashMap<Long, GroupChannel> availableChannels = new ConcurrentHashMap<>();

    private static Long counter = 0L;

    public Long createChannel(String title) {
        availableChannels.put(++counter, new GroupChannel(title));
        return counter;
    }

    public boolean subscribe(Long id, WebSocketSession session) {
        AtomicBoolean result = new AtomicBoolean(false);
        Optional.ofNullable(availableChannels.get(id)).ifPresent(channel -> result.set(channel.subscribe(session)));
        return result.get();
    }

    public boolean unsubscribe(Long id, WebSocketSession session) {
        AtomicBoolean result = new AtomicBoolean(false);
        Optional.ofNullable(availableChannels.get(id)).ifPresent(channel -> result.set(channel.unsubscribe(session)));
        return result.get();
    }

    public boolean checkParticipation(Long channelId, WebSocketSession session) {
        return availableChannels.get(channelId).getSessions().contains(session);
    }

    public ConcurrentLinkedDeque<WebSocketSession> getSessions(Long id) {
        return availableChannels.get(id).getSessions();
    }
}
