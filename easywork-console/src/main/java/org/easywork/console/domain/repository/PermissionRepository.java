package org.easywork.console.domain.repository;

import org.easywork.console.domain.model.Permission;
import org.easywork.console.domain.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 09:08
 */
public interface PermissionRepository extends BaseRepository<Permission> {
    /**
     * 根据权限代码查找权限
     */
    Optional<Permission> findByCode(String code);

    /**
     * 查找所有权限（树形结构）
     */
    List<Permission> findAllAsTree();

    /**
     * 根据父ID查找子权限
     */
    List<Permission> findByParentId(Long parentId);

    /**
     * 根据用户ID查找权限
     */
    List<Permission> findByUserId(Long userId);

    /**
     * 根据角色ID查找权限
     */
    List<Permission> findByRoleId(Long roleId);

    /**
     * 根据类型查找权限
     */
    List<Permission> findByType(Integer type);

    /**
     * 查找所有启用的权限
     */
    List<Permission> findAllEnabled();

    /**
     * 检查权限代码是否存在
     */
    boolean existsByCode(String code);

}
