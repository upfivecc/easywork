package org.easywork.console.infra.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.easywork.console.infra.repository.po.RolePO;

import java.util.List;

/**
 * 角色Mapper接口
 * 
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Mapper
public interface RoleMapper extends BaseMapper<RolePO> {
    
    /**
     * 根据用户ID查找角色（复杂关联查询）
     */
    @Select("""  
        SELECT r.* FROM sys_role r 
        INNER JOIN sys_user_role ur ON r.id = ur.role_id 
        WHERE ur.user_id = #{userId} 
        AND r.deleted = 0 
        AND ur.deleted = 0
        ORDER BY r.sort ASC, r.create_time DESC
        """)
    List<RolePO> selectByUserId(@Param("userId") Long userId);
}