package org.easywork.auth.infra.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;

/**
 * 短信登录的 认证令牌
 * 
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 11:04
 */
public class SmsAuthenticationToken extends AbstractAuthenticationToken {
    @Serial
    private static final long serialVersionUID = -5980591373743506236L;

    /**
     * 手机号
     */
    private final Object principal;
    /**
     * 验证码
     */
    private final Object credentials;

    public SmsAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public SmsAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = null;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
