package com.example.testwebsockets.service;

import com.example.testwebsockets.channel.ChannelManager;
import com.example.testwebsockets.model.RequestMessageTO;
import com.example.testwebsockets.model.ResponseMessageTO;
import com.example.testwebsockets.util.JsonWsConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class BroadcastService {

    private final JsonWsConverter converter;

    private final ChannelManager channelManager;

    public BroadcastService(JsonWsConverter converter,
                            ChannelManager channelManager) {
        this.converter = converter;
        this.channelManager = channelManager;
    }


    public void send(WebSocketSession session, RequestMessageTO request) throws Exception {
        if (!channelManager.checkParticipation(request.getChannelId(), session)) {
            throw new Exception("Cannot send a message");
        }
        ResponseMessageTO response = ResponseMessageTO.builder()
                .id(request.getId())
                .text(request.getText())
                .timestamp(LocalDateTime.now())
                .build();
        channelManager.getSessions(request.getChannelId()).forEach(s -> {
            try {
                s.sendMessage(new TextMessage(converter.convert(response).asBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
