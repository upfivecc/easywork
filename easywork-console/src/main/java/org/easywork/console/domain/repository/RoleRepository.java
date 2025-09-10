package org.easywork.console.domain.repository;

import org.easywork.console.domain.model.Role;
import org.easywork.console.domain.model.dto.RoleQuery;
import org.easywork.console.domain.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 09:08
 */
public interface RoleRepository extends BaseRepository<Role, RoleQuery> {

    /**
     * 根据角色代码查找角色
     */
    Optional<Role> findByCode(String code);

    /**
     * 分页查询角色
     */
    List<Role> findByPage(int page, int size, String keyword);

    /**
     * 统计角色数量
     */
    long count(String keyword);

    /**
     * 查找所有启用的角色
     */
    List<Role> findAllEnabled();

    /**
     * 根据用户ID查找角色
     */
    List<Role> findByUserId(Long userId);

    /**
     * 检查角色代码是否存在
     */
    boolean existsByCode(String code);

}
