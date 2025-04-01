package com.maybach7.formhandler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class Session {

    private String sessionId;
    private Integer userId;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public Session(Integer userId, LocalDateTime createdAt, LocalDateTime expiresAt) {
        sessionId = UUID.randomUUID().toString();
        this.userId = userId;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }
}
