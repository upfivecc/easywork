package org.easywork.auth.infra.repository;

import lombok.RequiredArgsConstructor;
import org.easywork.auth.domain.user.model.AuthUser;
import org.easywork.auth.domain.user.model.AuthUserQuery;
import org.easywork.auth.domain.user.respository.AuthUserRepository;
import org.easywork.auth.infra.repository.converter.AuthUserConverter;
import org.easywork.auth.infra.repository.po.AuthUserPO;
import org.easywork.common.rest.result.PageInfo;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 21:48
 */
@RequiredArgsConstructor
@Component
public class AuthUserRepositoryImpl implements AuthUserRepository {

    private final JpaAuthUserRepository jpaAuthUserRepository;

    @Override
    public AuthUser findByUsername(String username) {
        Example<AuthUserPO> example = Example.of(AuthUserPO.builder().username(username).build());
        Optional<AuthUserPO> optional = jpaAuthUserRepository.findOne(example);
        return optional.map(AuthUserConverter.INSTANCE::po2bo).orElse(null);
    }

    @Override
    public AuthUser findByPhone(String phone) {
        Example<AuthUserPO> example = Example.of(AuthUserPO.builder().phone(phone).build());
        Optional<AuthUserPO> optional = jpaAuthUserRepository.findOne(example);
        return optional.map(AuthUserConverter.INSTANCE::po2bo).orElse(null);
    }

    @Override
    public AuthUser loadById(Long userId) {
        AuthUserPO authUserPO = jpaAuthUserRepository.getReferenceById(userId);
        return AuthUserConverter.INSTANCE.po2bo(authUserPO);
    }

    @Override
    public PageInfo<AuthUser> pageQuery(AuthUserQuery query) {
        Example<AuthUserPO> example = Example.of(AuthUserPO.builder()
                .build());
        Pageable pageable = PageRequest.of(query.getPageNum() - 1, query.getPageSize(), Sort.by("id").descending());
        Page<AuthUserPO> page = jpaAuthUserRepository.findAll(example, pageable);
        List<AuthUser> authUsers = AuthUserConverter.INSTANCE.po2bo(page.getContent());
        return PageInfo.of(authUsers, page.getTotalElements(), page.getNumber(), page.getSize(), page.getTotalPages());
    }
}
