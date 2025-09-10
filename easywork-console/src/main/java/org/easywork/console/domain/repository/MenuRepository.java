package org.easywork.console.domain.repository;

import org.easywork.console.domain.model.Menu;

import java.util.List;
import java.util.Optional;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 09:08
 */
public interface MenuRepository {

    /**
     * 保存菜单
     */
    Menu save(Menu menu);

    /**
     * 根据ID查找菜单
     */
    Optional<Menu> findById(Long id);

    /**
     * 根据菜单代码查找菜单
     */
    Optional<Menu> findByCode(String code);

    /**
     * 查找所有菜单（树形结构）
     */
    List<Menu> findAllAsTree();

    /**
     * 根据父ID查找子菜单
     */
    List<Menu> findByParentId(Long parentId);

    /**
     * 根据用户ID查找菜单
     */
    List<Menu> findByUserId(Long userId);

    /**
     * 根据角色ID查找菜单
     */
    List<Menu> findByRoleId(Long roleId);

    /**
     * 根据类型查找菜单
     */
    List<Menu> findByType(Integer type);

    /**
     * 查找所有可见的菜单
     */
    List<Menu> findAllVisible();

    /**
     * 查找所有启用的菜单
     */
    List<Menu> findAllEnabled();

    /**
     * 检查菜单代码是否存在
     */
    boolean existsByCode(String code);

    /**
     * 删除菜单
     */
    void deleteById(Long id);

    /**
     * 批量删除菜单
     */
    void deleteByIds(List<Long> ids);
}
