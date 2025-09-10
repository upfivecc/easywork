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
     * 根据角色ID查找用户（复杂关联查询）
     * 此方法涉及多表关联，适合使用 @Select 注解
     */
    @Select("""
            SELECT u.* FROM sys_user u 
            INNER JOIN sys_user_role ur ON u.id = ur.user_id 
            WHERE ur.role_id = #{roleId} AND u.deleted = 0 
            ORDER BY u.create_time DESC
            """)
    List<UserPO> selectByRoleId(@Param("roleId") Long roleId);
}
