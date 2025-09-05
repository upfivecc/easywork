package org.easywork.auth.domain.token.service;

import cn.hutool.core.util.IdUtil;
import org.easywork.common.exception.BizException;
import org.easywork.common.rest.result.ResultCode;
import org.easywork.auth.domain.token.model.AuthRefreshToken;
import org.easywork.auth.domain.token.repository.AuthRefreshTokenRepository;
import org.easywork.auth.domain.user.model.AuthUser;
import org.easywork.auth.domain.user.service.AuthUserService;
import org.easywork.auth.infra.jwt.JwtTokenService;
import org.easywork.auth.trigger.http.rest.dto.AuthTokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/5 10:54
 */
@RequiredArgsConstructor
@Service
public class AuthTokenServiceImpl implements AuthTokenService {


    private final AuthRefreshTokenRepository authRefreshTokenRepository;
    private final AuthUserService authUserService;
    private final JwtTokenService jwtTokenService;

    @Override
    public AuthTokenDTO generateToken(Authentication authentication) {
        String name = authentication.getName();
        AuthUser authUser = this.authUserService.loadByUsername(name);
        AuthTokenDTO token = new AuthTokenDTO();
        token.setToken(jwtTokenService.generateToken(authUser));
        token.setRefreshToken(this.generateRefreshToken(authentication));
        return token;
    }

    private String generateRefreshToken(Authentication authentication) {
        AuthUser authUser = this.authUserService.loadByUsername(authentication.getName());
        if (null == authUser) {
            throw new BizException(ResultCode.USER_NOT_EXIST);
        }
        String refreshToken = IdUtil.fastSimpleUUID();
        Instant now = Instant.now();

        AuthRefreshToken authRefreshToken = AuthRefreshToken.builder()
                .clientId("default")
                .userId(authUser.getId())
                .refreshToken(refreshToken)
                .expireAt(now.plus(30, ChronoUnit.DAYS))
                .build();
        this.authRefreshTokenRepository.save(authRefreshToken);
        return refreshToken;
    }

    @Override
    public Authentication parseToken(String token) {
        AuthUser authUser = jwtTokenService.parseToken(token);
        return new UsernamePasswordAuthenticationToken(authUser, "", authUser.getAuthorities());
    }

    @Override
    public AuthTokenDTO refreshToken(String refreshToken) {
        AuthRefreshToken authRefreshToken = this.authRefreshTokenRepository.findByRefreshToken(refreshToken);
        if (null == authRefreshToken || authRefreshToken.getExpireAt().isBefore(Instant.now())) {
            throw new BizException(ResultCode.INVALID_TOKEN);
        }
        AuthUser authUser = this.authUserService.loadById(authRefreshToken.getUserId());
        String token = jwtTokenService.generateToken(authUser);
        AuthTokenDTO authTokenDTO = new AuthTokenDTO();
        authTokenDTO.setToken(token);
        authTokenDTO.setRefreshToken(refreshToken);
        return authTokenDTO;
    }

}
