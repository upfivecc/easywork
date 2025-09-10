package org.easywork.console.infra.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.easywork.console.infra.repository.po.MenuPO;

import java.util.List;

/**
 * 菜单Mapper接口
 * 
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuPO> {
    
    /**
     * 根据用户ID查找菜单（复杂关联查询）
     */
    @Select("""  
        SELECT DISTINCT m.* FROM sys_menu m 
        INNER JOIN sys_role_menu rm ON m.id = rm.menu_id 
        INNER JOIN sys_user_role ur ON rm.role_id = ur.role_id 
        WHERE ur.user_id = #{userId} 
        AND m.deleted = 0 
        AND rm.deleted = 0
        AND ur.deleted = 0
        ORDER BY m.level ASC, m.sort ASC
        """)
    List<MenuPO> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 根据角色ID查找菜单（复杂关联查询）
     */
    @Select("""  
        SELECT m.* FROM sys_menu m 
        INNER JOIN sys_role_menu rm ON m.id = rm.menu_id 
        WHERE rm.role_id = #{roleId} 
        AND m.deleted = 0 
        AND rm.deleted = 0
        ORDER BY m.level ASC, m.sort ASC
        """)
    List<MenuPO> selectByRoleId(@Param("roleId") Long roleId);
}