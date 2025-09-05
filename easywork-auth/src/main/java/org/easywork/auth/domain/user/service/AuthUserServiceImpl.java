package org.easywork.auth.domain.user.service;

import org.easywork.auth.domain.user.model.AuthUser;
import org.easywork.auth.domain.user.model.AuthUserQuery;
import org.easywork.auth.domain.user.respository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.easywork.common.rest.result.PageInfo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 09:50
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements UserDetailsService, AuthUserService {

    private final AuthUserRepository authUserRepository;

    @Override
    public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByUsername(username);
        if (null == authUser) {
            throw new UsernameNotFoundException("User not exist");
        }
        this.loadAuthorizes(authUser);
        return authUser;
    }

    @Override
    public AuthUser loadUserByPhone(String phone) {
        AuthUser authUser = authUserRepository.findByPhone(phone);
        this.loadAuthorizes(authUser);
        return authUser;
    }

    @Override
    public AuthUser loadByUsername(String username) {
        AuthUser authUser = authUserRepository.findByUsername(username);
        this.loadAuthorizes(authUser);
        return authUser;
    }

    @Override
    public AuthUser loadById(Long userId) {
        AuthUser authUser = authUserRepository.loadById(userId);
        this.loadAuthorizes(authUser);
        return authUser;
    }

    @Override
    public PageInfo<AuthUser> pageQuery(AuthUserQuery query) {
        PageInfo<AuthUser> pageInfo = authUserRepository.pageQuery(query);
        pageInfo.getRecords().forEach(this::loadAuthorizes);
        return pageInfo;
    }

    private void loadAuthorizes(AuthUser authUser) {
        authUser.setAuthorities(Set.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
