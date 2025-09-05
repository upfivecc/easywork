package org.easywork.auth.infra.jwt;

import org.easywork.auth.domain.user.model.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtTokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }


    /**
     * 生成 Token
     */
    public String generateToken(AuthUser authUser) {
        Instant now = Instant.now();
        // 1小时有效期，可以放到配置文件
        long expiry = 3600L;

        String authorities = authUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self") // 签发者，可以自定义
                .issuedAt(now)
                .expiresAt(now.plus(expiry, ChronoUnit.SECONDS))
                .subject(authUser.getUsername())
                .claim("userid", authUser.getId())
                .claim("authorities", authorities)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(SignatureAlgorithm.RS256).keyId("custom-key-id").build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    /**
     * 解析 Token
     */
    public AuthUser parseToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            String username = jwt.getSubject();
            Long userId = jwt.getClaim("userid");
            String authorities = jwt.getClaimAsString("authorities");
            return AuthUser.builder()
                    .id(userId)
                    .username(username)
                    .authorities(authorities == null ?
                            Set.of():
                            Arrays.stream(authorities.split(","))
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toSet()))
                    .build();
        } catch (JwtException e) {
            // Token 无效或过期
            return null;
        }
    }
}