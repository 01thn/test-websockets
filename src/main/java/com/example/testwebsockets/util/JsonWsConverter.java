package com.example.testwebsockets.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

@Component
public class JsonWsConverter {
    private final ObjectMapper objectMapper;

    public JsonWsConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public TextMessage convert(Object data) {
        return new TextMessage(objectMapper.writeValueAsString(data));
    }
}
