package org.easywork.console.domain.repository;

import org.easywork.console.domain.model.Dept;

import java.util.List;
import java.util.Optional;

/**
 * 部门仓储接口
 * 
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
public interface DeptRepository {
    
    /**
     * 保存部门
     */
    Dept save(Dept dept);
    
    /**
     * 根据ID查找部门
     */
    Optional<Dept> findById(Long id);
    
    /**
     * 根据部门编码查找部门
     */
    Optional<Dept> findByCode(String code);
    
    /**
     * 查找所有部门（树形结构）
     */
    List<Dept> findAllAsTree();
    
    /**
     * 根据父ID查找子部门
     */
    List<Dept> findByParentId(Long parentId);
    
    /**
     * 根据类型查找部门
     */
    List<Dept> findByType(Integer type);
    
    /**
     * 查找所有启用的部门
     */
    List<Dept> findAllEnabled();
    
    /**
     * 根据负责人ID查找部门
     */
    List<Dept> findByLeaderId(Long leaderId);
    
    /**
     * 检查部门编码是否存在
     */
    boolean existsByCode(String code);
    
    /**
     * 删除部门
     */
    void deleteById(Long id);
    
    /**
     * 批量删除部门
     */
    void deleteByIds(List<Long> ids);
    
    /**
     * 查找指定部门的所有子部门ID（包括孙部门）
     */
    List<Long> findAllChildrenIds(Long deptId);
    
    /**
     * 查找指定用户数据权限范围内的部门
     */
    List<Dept> findByDataScope(Long userId, Integer dataScope);
}