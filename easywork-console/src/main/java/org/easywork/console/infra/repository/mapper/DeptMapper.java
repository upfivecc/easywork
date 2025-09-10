package org.easywork.console.infra.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.easywork.console.infra.repository.po.DeptPO;

import java.util.List;

/**
 * 部门Mapper接口
 * 基于 code 的树形结构查询
 * 
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Mapper
public interface DeptMapper extends BaseMapper<DeptPO> {
    
    /**
     * 根据部门编码查询所有子部门（包括孙部门）
     * 基于 code 的树形结构查询
     */
    @Select("""
        SELECT * 
        FROM sys_dept 
        WHERE (
            path LIKE CONCAT(
                (SELECT path FROM sys_dept WHERE code = #{deptCode} AND deleted = 0), 
                '/%'
            ) 
            OR code = #{deptCode}
        ) 
        AND deleted = 0
        ORDER BY level ASC, sort ASC
        """)
    List<DeptPO> selectAllChildrenByCode(@Param("deptCode") String deptCode);
}