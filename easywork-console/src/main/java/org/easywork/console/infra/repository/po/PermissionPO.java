package org.easywork.console.infra.repository.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.infra.repository.po.base.BasePO;

/**
 * 权限实体
 * 基于 code 进行树形结构关联
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@TableName(value = "sys_permission")
@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionPO extends BasePO {

    /**
     * 权限名称
     */
    @TableField("name")
    private String name;

    /**
     * 权限代码
     */
    @TableField("code")
    private String code;

    /**
     * 权限类型 1-菜单 2-按钮 3-API
     */
    @TableField("type")
    private Integer type;

    /**
     * 权限描述
     */
    @TableField("description")
    private String description;

    /**
     * 资源路径（API路径或前端路由）
     */
    @TableField("resource")
    private String resource;

    /**
     * HTTP方法（GET、POST、PUT、DELETE等）
     */
    @TableField("method")
    private String method;

    /**
     * 状态 1-启用 0-禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 父权限编码
     */
    @TableField("parent_code")
    private String parentCode;

    /**
     * 节点层级
     */
    @TableField("level")
    private Integer level;

    /**
     * 排序号
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 路径（用于快速查询子树）
     */
    @TableField("path")
    private String path;

}