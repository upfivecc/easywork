package org.easywork.auth.infra.repository;

import org.easywork.auth.infra.repository.po.AuthClientPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 14:47
 */
public interface JpaAuthClientRepository extends JpaRepository<AuthClientPO, Long> {
}
