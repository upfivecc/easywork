package org.easywork.auth.infra.repository;

import org.easywork.auth.infra.repository.po.AuthRefreshTokenPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/5 10:47
 */
public interface JpaAuthRefreshTokenRepository extends JpaRepository<AuthRefreshTokenPO, Long> {
}
