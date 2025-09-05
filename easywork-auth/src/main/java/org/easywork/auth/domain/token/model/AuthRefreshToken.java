package org.easywork.auth.domain.token.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/5 10:45
 */
@Builder
@Data
public class AuthRefreshToken {
    private String clientId;
    private Long userId;
    private String refreshToken;
    private Instant expireAt;
    private String extra;
}
