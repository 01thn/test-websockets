package com.example.testwebsockets.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessageTO {
    private Long id;
    private String text;
    private LocalDateTime timestamp;
}
