package com.example.testwebsockets.handler;

import com.example.testwebsockets.channel.ChannelManager;
import com.example.testwebsockets.model.RequestMessageTO;
import com.example.testwebsockets.service.BroadcastService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    private final SubscribeHandler subscribeHandler;

    private final BroadcastService broadcastService;

    private final ChannelManager channelManager;

    public SocketHandler(ObjectMapper objectMapper,
                         SubscribeHandler subscribeHandler,
                         BroadcastService broadcastService,
                         ChannelManager channelManager) {
        this.objectMapper = objectMapper;
        this.subscribeHandler = subscribeHandler;
        this.broadcastService = broadcastService;
        this.channelManager = channelManager;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        RequestMessageTO request = objectMapper.readValue(message.getPayload(), RequestMessageTO.class);
        if (request.getType() != null) {
            subscribeHandler.handle(session, request);
        } else {
            broadcastService.send(session, request);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println(channelManager.createChannel("Testchannel"));
    }
}
