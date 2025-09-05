package org.easywork.auth.domain.token.service;

import org.easywork.auth.trigger.http.rest.dto.AuthTokenDTO;
import org.springframework.security.core.Authentication;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/5 10:53
 */
public interface AuthTokenService {

    AuthTokenDTO generateToken(Authentication authentication);

    Authentication parseToken(String token);

    AuthTokenDTO refreshToken(String refreshToken);
}
