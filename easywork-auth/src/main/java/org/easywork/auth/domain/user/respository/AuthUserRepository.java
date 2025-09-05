package org.easywork.auth.domain.user.respository;

import org.easywork.auth.domain.user.model.AuthUser;
import org.easywork.auth.domain.user.model.AuthUserQuery;
import org.easywork.common.rest.result.PageInfo;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 21:42
 */
public interface AuthUserRepository {
    AuthUser findByUsername(String username);
    AuthUser findByPhone(String phone);

    AuthUser loadById(Long userId);

    PageInfo<AuthUser> pageQuery(AuthUserQuery query);
}
