package org.easywork.auth.infra.repository;

import org.easywork.auth.domain.client.model.AuthClient;
import org.easywork.auth.domain.client.repository.AuthClientRepository;
import org.easywork.auth.infra.repository.converter.AuthClientConverter;
import org.easywork.auth.infra.repository.po.AuthClientPO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: fiveupup
 * @version: 1.0.0
 * @date: 2025/9/4 21:43
 */
@RequiredArgsConstructor
@Component
public class AuthClientRepositoryImpl implements AuthClientRepository {

    private final JpaAuthClientRepository jpaAuthClientRepository;

    @Override
    public AuthClient findById(Long id) {
        Optional<AuthClientPO> optional = jpaAuthClientRepository.findById(id);
        return optional.map(AuthClientConverter.INSTANCE::po2bo).orElse(null);
    }

    @Override
    public AuthClient findByClientId(String clientId) {
        Example<AuthClientPO> example = Example.of(AuthClientPO.builder().clientId(clientId).build());
        Optional<AuthClientPO> optional = jpaAuthClientRepository.findOne(example);
        return optional.map(AuthClientConverter.INSTANCE::po2bo).orElse(null);
    }
}
