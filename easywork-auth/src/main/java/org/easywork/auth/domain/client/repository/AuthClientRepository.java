package org.easywork.auth.domain.client.repository;

import org.easywork.auth.domain.client.model.AuthClient;

/**
 * @author: fiveupup
 * @version: 1.0.0
 * @date: 2025/9/4 21:41
 */
public interface AuthClientRepository {

    AuthClient findById(Long id);

    AuthClient findByClientId(String clientId);
}
