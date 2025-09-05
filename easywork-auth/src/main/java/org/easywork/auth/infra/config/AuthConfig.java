package org.easywork.auth.infra.config;

import org.easywork.auth.domain.user.service.AuthUserService;
import org.easywork.auth.infra.jwt.JwtTokenService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/2 11:02
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class AuthConfig {


    /**
     * 授权服务器配置
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizeServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer oAuth2AuthorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();
        oAuth2AuthorizationServerConfigurer.authorizationEndpoint(authorization -> authorization.consentPage("/oauth2/consent"));
        http.securityMatcher(oAuth2AuthorizationServerConfigurer.getEndpointsMatcher());
        http.with(oAuth2AuthorizationServerConfigurer, authorizationServer -> authorizationServer.oidc(Customizer.withDefaults()));

        http
                .securityMatcher("/oauth2/**", "/userinfo")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/oauth2/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/oauth2/login")
                        .loginProcessingUrl("/oauth2/login")
                        .permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/oauth2/login"));
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenService jwtTokenService) throws Exception {
        http
                .securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/api/jwtLogin", "/api/smsSendCode", "/api/smsLogin").permitAll()
                        .anyRequest().authenticated()
                )
                // 表单登录（适合传统页面）
                .formLogin(form -> form
                        // 自定义登录页面
                        .loginPage("/login")
                        // 成功处理
                        .defaultSuccessUrl("/dashboard", true)
                        // 失败处理
                        .failureUrl("/login?error")
                )
                // JWT token 校验
                .addFilterAfter(new JwtAuthenticationFilter(jwtTokenService), UsernamePasswordAuthenticationFilter.class)
                // OAuth2 登录（支持微信、微博、GitHub）
                //.oauth2Login(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用BCrypt加密密码
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthUserService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider((UserDetailsService) userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider(userDetailsService);
        return new ProviderManager(List.of(smsAuthenticationProvider, daoAuthenticationProvider));
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(KeyPair rsaKeyPair) {
        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) rsaKeyPair.getPublic())
                .privateKey(rsaKeyPair.getPrivate())
                .keyID("custom-key-id")
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, context) -> jwkSelector.select(jwkSet);
    }

}
