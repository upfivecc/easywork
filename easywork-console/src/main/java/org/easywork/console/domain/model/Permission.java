package org.easywork.console.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.domain.model.base.TreeNode;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 09:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Permission extends TreeNode<Permission> {

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限代码
     */
    private String code;

    /**
     * 权限类型 1-菜单 2-按钮 3-API
     */
    private Integer type;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 资源路径（API路径或前端路由）
     */
    private String resource;

    /**
     * HTTP方法（GET、POST、PUT、DELETE等）
     */
    private String method;

    /**
     * 状态 1-启用 0-禁用
     */
    private Integer status;

    /**
     * 图标
     */
    private String icon;

    /**
     * 备注
     */
    private String remark;

    /**
     * 检查权限是否启用
     */
    public boolean isEnabled() {
        return status != null && status == 1;
    }

    /**
     * 是否为菜单权限
     */
    public boolean isMenu() {
        return type != null && type == 1;
    }

    /**
     * 是否为按钮权限
     */
    public boolean isButton() {
        return type != null && type == 2;
    }

    /**
     * 是否为API权限
     */
    public boolean isApi() {
        return type != null && type == 3;
    }
}
