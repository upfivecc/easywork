package org.easywork.auth.domain.token.repository;

import org.easywork.auth.domain.token.model.AuthRefreshToken;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/5 10:45
 */
public interface AuthRefreshTokenRepository {

    AuthRefreshToken findByRefreshToken(String refreshToken);

    void save(AuthRefreshToken authRefreshToken);
}
