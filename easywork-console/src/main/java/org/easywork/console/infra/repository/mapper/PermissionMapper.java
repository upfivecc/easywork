package org.easywork.console.infra.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.easywork.console.infra.repository.po.PermissionPO;

import java.util.List;

/**
 * 权限Mapper接口
 * 
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Mapper
public interface PermissionMapper extends BaseMapper<PermissionPO> {
    
    /**
     * 根据用户ID查找权限（复杂关联查询）
     */
    @Select("""  
        SELECT DISTINCT p.* FROM sys_permission p 
        INNER JOIN sys_role_permission rp ON p.id = rp.permission_id 
        INNER JOIN sys_user_role ur ON rp.role_id = ur.role_id 
        WHERE ur.user_id = #{userId} 
        AND p.deleted = 0 
        AND rp.deleted = 0
        AND ur.deleted = 0
        ORDER BY p.level ASC, p.sort ASC
        """)
    List<PermissionPO> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 根据角色ID查找权限（复杂关联查询）
     */
    @Select("""  
        SELECT p.* FROM sys_permission p 
        INNER JOIN sys_role_permission rp ON p.id = rp.permission_id 
        WHERE rp.role_id = #{roleId} 
        AND p.deleted = 0 
        AND rp.deleted = 0
        ORDER BY p.level ASC, p.sort ASC
        """)
    List<PermissionPO> selectByRoleId(@Param("roleId") Long roleId);
}