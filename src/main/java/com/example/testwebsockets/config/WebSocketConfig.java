package com.example.testwebsockets.config;

import com.example.testwebsockets.handler.SocketHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.List;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SocketHandler socketHandler;

    @Value("${websocket.allowed-origins:*}")
    private List<String> allowedOrigins;

    public WebSocketConfig(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/ws").setAllowedOrigins(allowedOrigins.toArray(new String[0]));
    }
}
