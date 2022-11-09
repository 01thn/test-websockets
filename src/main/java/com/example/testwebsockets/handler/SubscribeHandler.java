package com.example.testwebsockets.handler;

import com.example.testwebsockets.channel.ChannelManager;
import com.example.testwebsockets.channel.GroupChannel;
import com.example.testwebsockets.model.RequestMessageTO;
import com.example.testwebsockets.model.RequestType;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
public class SubscribeHandler {

    private final ChannelManager channelManager;

    public SubscribeHandler(ChannelManager channelManager) {
        this.channelManager = channelManager;
    }

    public void handle(WebSocketSession session, RequestMessageTO request) throws IOException {
        if (request.getType() == RequestType.SUBSCRIBE) {
            if (channelManager.subscribe(request.getChannelId(), session)) {
                session.sendMessage(new TextMessage("You've been successfully joined to channel"));
            } else
                session.sendMessage(new TextMessage("Cannot join this channel"));
        }
        if (request.getType() == RequestType.UNSUBSCRIBE) {
            channelManager.unsubscribe(request.getChannelId(), session);
            session.sendMessage(new TextMessage("You've been successfully removed"));
        }
    }
}
