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
     * 根据父ID查询子部门
     */
    @Select("SELECT * FROM sys_dept WHERE parent_id = #{parentId} AND deleted = 0 ORDER BY sort ASC")
    List<DeptPO> selectByParentId(@Param("parentId") Long parentId);
    
    /**
     * 根据部门编码查询部门
     */
    @Select("SELECT * FROM sys_dept WHERE code = #{code} AND deleted = 0 LIMIT 1")
    DeptPO selectByCode(@Param("code") String code);
    
    /**
     * 根据类型查询部门
     */
    @Select("SELECT * FROM sys_dept WHERE type = #{type} AND deleted = 0 ORDER BY level ASC, sort ASC")
    List<DeptPO> selectByType(@Param("type") Integer type);
    
    /**
     * 查询所有启用的部门
     */
    @Select("SELECT * FROM sys_dept WHERE status = 1 AND deleted = 0 ORDER BY level ASC, sort ASC")
    List<DeptPO> selectAllEnabled();
    
    /**
     * 根据负责人ID查询部门
     */
    @Select("SELECT * FROM sys_dept WHERE leader_id = #{leaderId} AND deleted = 0")
    List<DeptPO> selectByLeaderId(@Param("leaderId") Long leaderId);
    
    /**
     * 查询指定部门的所有子部门（包括孙部门）
     */
    @Select("SELECT * FROM sys_dept WHERE (path LIKE CONCAT((SELECT path FROM sys_dept WHERE id = #{deptId}), '/%') OR id = #{deptId}) AND deleted = 0")
    List<DeptPO> selectAllChildren(@Param("deptId") Long deptId);
    
    /**
     * 检查部门编码是否存在
     */
    @Select("SELECT COUNT(1) FROM sys_dept WHERE code = #{code} AND deleted = 0")
    int countByCode(@Param("code") String code);
    
    /**
     * 检查部门编码是否存在（排除指定ID）
     */
    @Select("SELECT COUNT(1) FROM sys_dept WHERE code = #{code} AND id != #{excludeId} AND deleted = 0")
    int countByCodeExcludeId(@Param("code") String code, @Param("excludeId") Long excludeId);
}