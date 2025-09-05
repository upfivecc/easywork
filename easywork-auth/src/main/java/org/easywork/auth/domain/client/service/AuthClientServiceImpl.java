package org.easywork.auth.domain.client.service;

import org.easywork.auth.domain.client.model.AuthClient;
import org.easywork.auth.domain.client.repository.AuthClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 14:51
 */
@RequiredArgsConstructor
@Component
public class AuthClientServiceImpl implements RegisteredClientRepository, AuthClientService {

    private final AuthClientRepository authClientRepository;

    @Override
    public void save(RegisteredClient registeredClient) {
    }

    @Override
    public RegisteredClient findById(String id) {
        AuthClient authClient = authClientRepository.findById(Long.valueOf(id));
        return getRegisteredClient(authClient);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        AuthClient authClient = authClientRepository.findByClientId(clientId);
        return getRegisteredClient(authClient);
    }

    private RegisteredClient getRegisteredClient(AuthClient authClient) {
        if (null == authClient) {
            return null;
        }
        return RegisteredClient.withId(String.valueOf(authClient.getId()))
                .clientId(authClient.getClientId())
                .clientSecret(authClient.getClientSecret())
                .authorizationGrantTypes(t -> t.addAll(Set.of(AuthorizationGrantType.AUTHORIZATION_CODE
                        , AuthorizationGrantType.REFRESH_TOKEN, AuthorizationGrantType.CLIENT_CREDENTIALS)))
                .scopes(t -> t.addAll(Arrays.asList(authClient.getScope().split(","))))
                .redirectUri(authClient.getRedirectUri())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .build();
    }
}
