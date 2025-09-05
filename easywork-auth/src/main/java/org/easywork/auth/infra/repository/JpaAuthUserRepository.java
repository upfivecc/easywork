package org.easywork.auth.infra.repository;

import org.easywork.auth.infra.repository.po.AuthUserPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 10:02
 */
public interface JpaAuthUserRepository extends JpaRepository<AuthUserPO, Long> {
}
