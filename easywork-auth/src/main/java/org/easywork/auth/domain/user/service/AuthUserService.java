package org.easywork.auth.domain.user.service;

import org.easywork.auth.domain.user.model.AuthUser;
import org.easywork.auth.domain.user.model.AuthUserQuery;
import org.easywork.common.rest.result.PageInfo;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 09:52
 */
public interface AuthUserService {

    AuthUser loadUserByPhone(String phone);

    AuthUser loadByUsername(String username);

    AuthUser loadById(Long userId);

    PageInfo<AuthUser> pageQuery(AuthUserQuery query);
}
