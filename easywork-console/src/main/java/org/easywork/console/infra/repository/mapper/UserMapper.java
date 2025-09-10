package org.easywork.console.infra.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.easywork.console.infra.repository.po.UserPO;

import java.util.List;

/**
 * 用户仓储接口
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
public interface UserMapper extends BaseMapper<UserPO> {

    /**
     * 根据用户名查找用户
     */
    @Select("SELECT * FROM sys_user WHERE username = #{username} AND deleted = 0")
    UserPO selectByUsername(@Param("username") String username);

    /**
     * 根据邮箱查找用户
     */
    @Select("SELECT * FROM sys_user WHERE email = #{email} AND deleted = 0")
    UserPO selectByEmail(@Param("email") String email);

    /**
     * 根据手机号查找用户
     */
    @Select("SELECT * FROM sys_user WHERE phone = #{phone} AND deleted = 0")
    UserPO selectByPhone(@Param("phone") String phone);

    /**
     * 根据部门ID查找用户
     */
    @Select("SELECT * FROM sys_user WHERE dept_id = #{deptId} AND deleted = 0")
    List<UserPO> selectByDeptId(@Param("deptId") Long deptId);

    /**
     * 根据角色ID查找用户
     */
    @Select("SELECT u.* FROM sys_user u " +
            "INNER JOIN sys_user_role ur ON u.id = ur.user_id " +
            "WHERE ur.role_id = #{roleId} AND u.deleted = 0")
    List<UserPO> selectByRoleId(@Param("roleId") Long roleId);

    /**
     * 统计用户名存在数量
     */
    @Select("SELECT COUNT(*) FROM sys_user WHERE username = #{username} AND deleted = 0")
    Long countByUsername(@Param("username") String username);

    /**
     * 统计邮箱存在数量
     */
    @Select("SELECT COUNT(*) FROM sys_user WHERE email = #{email} AND deleted = 0")
    Long countByEmail(@Param("email") String email);

    /**
     * 统计手机号存在数量
     */
    @Select("SELECT COUNT(*) FROM sys_user WHERE phone = #{phone} AND deleted = 0")
    Long countByPhone(@Param("phone") String phone);
}
