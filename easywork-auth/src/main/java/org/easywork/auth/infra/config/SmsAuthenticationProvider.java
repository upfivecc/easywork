package org.easywork.auth.infra.config;

import org.easywork.auth.domain.user.service.AuthUserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 10:58
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private final AuthUserService authUserService;

    public SmsAuthenticationProvider(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phone = (String) authentication.getPrincipal();
        String code = (String) authentication.getCredentials();

        if (!code.equals("123456")) {
            throw new BadCredentialsException("sms code invalid");
        }

        UserDetails userDetails = this.authUserService.loadUserByPhone(phone);
        if (userDetails == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
