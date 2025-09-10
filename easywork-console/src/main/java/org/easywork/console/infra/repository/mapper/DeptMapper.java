package org.easywork.console.infra.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.easywork.console.infra.repository.po.DeptPO;

import java.util.List;

/**
 * 部门Mapper接口
 * 
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Mapper
public interface DeptMapper extends BaseMapper<DeptPO> {
    
    /**
     * 查询指定部门的所有子部门（包括孙部门）
     * 这是一个复杂的路径匹配查询，适合使用 @Select 注解
     * 使用 path 字段的 LIKE 查询实现树形结构的子节点查找
     */
    @Select("""
        SELECT * 
        FROM sys_dept 
        WHERE (
            path LIKE CONCAT(
                (SELECT path FROM sys_dept WHERE id = #{deptId}), 
                '/%'
            ) 
            OR id = #{deptId}
        ) 
        AND deleted = 0
        ORDER BY level ASC, sort ASC
        """)
    List<DeptPO> selectAllChildren(@Param("deptId") Long deptId);
}