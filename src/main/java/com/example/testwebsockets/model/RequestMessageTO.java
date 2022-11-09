package com.example.testwebsockets.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestMessageTO {
    private RequestType type;
    private Long channelId;
    private Long id;
    private String text;
}
