package org.easywork.auth.infra.repository;

import org.easywork.auth.domain.token.model.AuthRefreshToken;
import org.easywork.auth.domain.token.repository.AuthRefreshTokenRepository;
import org.easywork.auth.infra.repository.converter.AuthRefreshTokenConverter;
import org.easywork.auth.infra.repository.po.AuthRefreshTokenPO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/5 10:46
 */
@RequiredArgsConstructor
@Component
public class AuthRefreshTokenRepositoryImpl implements AuthRefreshTokenRepository {

    private final JpaAuthRefreshTokenRepository jpaAuthRefreshTokenRepository;

    @Override
    public AuthRefreshToken findByRefreshToken(String refreshToken) {
        Example<AuthRefreshTokenPO> example = Example.of(AuthRefreshTokenPO.builder().refreshToken(refreshToken).build());
        Optional<AuthRefreshTokenPO> optional = jpaAuthRefreshTokenRepository.findOne(example);
        return optional.map(AuthRefreshTokenConverter.INSTANCE::po2bo).orElse(null);
    }

    @Override
    public void save(AuthRefreshToken authRefreshToken) {
        AuthRefreshTokenPO authRefreshTokenPO = AuthRefreshTokenConverter.INSTANCE.bo2po(authRefreshToken);
        this.jpaAuthRefreshTokenRepository.save(authRefreshTokenPO);
    }
}
