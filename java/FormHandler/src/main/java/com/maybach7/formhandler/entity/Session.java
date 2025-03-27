package com.maybach7.formhandler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Session {

    private String sessionId;
    private Integer userId;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
