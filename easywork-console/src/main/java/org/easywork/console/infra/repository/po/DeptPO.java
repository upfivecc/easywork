package org.easywork.console.infra.repository.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.infra.repository.po.base.BasePO;

/**
 * 部门持久化对象
 * 
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
public class DeptPO extends BasePO {
    
    /**
     * 父部门ID
     */
    @TableField("parent_id")
    private Long parentId;
    
    /**
     * 部门名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 部门编码
     */
    @TableField("code")
    private String code;
    
    /**
     * 部门类型 1-公司 2-部门 3-小组
     */
    @TableField("type")
    private Integer type;
    
    /**
     * 部门负责人ID
     */
    @TableField("leader_id")
    private Long leaderId;
    
    /**
     * 部门负责人姓名
     */
    @TableField("leader_name")
    private String leaderName;
    
    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;
    
    /**
     * 邮箱
     */
    @TableField("email")
    private String email;
    
    /**
     * 部门地址
     */
    @TableField("address")
    private String address;
    
    /**
     * 状态 1-启用 0-禁用
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 部门层级
     */
    @TableField("level")
    private Integer level;
    
    /**
     * 排序号
     */
    @TableField("sort")
    private Integer sort;
    
    /**
     * 层级路径
     */
    @TableField("path")
    private String path;
    
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}