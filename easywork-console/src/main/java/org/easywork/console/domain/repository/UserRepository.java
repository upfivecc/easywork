package org.easywork.console.domain.repository;

import org.easywork.console.domain.model.User;
import org.easywork.console.domain.model.dto.UserQuery;
import org.easywork.console.domain.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 09:07
 */
public interface UserRepository extends BaseRepository<User, UserQuery> {

    /**
     * 根据用户名查找用户
     */
    Optional<User> findByUsername(String username);

    /**
     * 根据邮箱查找用户
     */
    Optional<User> findByEmail(String email);

    /**
     * 根据手机号查找用户
     */
    Optional<User> findByPhone(String phone);

    /**
     * 根据部门ID查找用户
     */
    List<User> findByDeptId(Long deptId);

    /**
     * 根据角色ID查找用户
     */
    List<User> findByRoleId(Long roleId);

    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查邮箱是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 检查手机号是否存在
     */
    boolean existsByPhone(String phone);

}
