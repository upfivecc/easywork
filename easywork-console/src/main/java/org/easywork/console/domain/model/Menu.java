package org.easywork.console.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.domain.model.base.TreeNode;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 09:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Menu extends TreeNode<Menu> {
    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单代码
     */
    private String code;

    /**
     * 菜单类型 1-目录 2-菜单 3-按钮
     */
    private Integer type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 路由路径
     */
    private String routePath;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 状态 1-显示 0-隐藏
     */
    private Integer visible;

    /**
     * 状态 1-启用 0-禁用
     */
    private Integer status;

    /**
     * 是否缓存 1-缓存 0-不缓存
     */
    private Integer cache;

    /**
     * 是否外链 1-是 0-否
     */
    private Integer external;

    /**
     * 外链地址
     */
    private String externalUrl;

    /**
     * 备注
     */
    private String remark;

    /**
     * 检查菜单是否可见
     */
    public boolean isVisible() {
        return visible != null && visible == 1;
    }

    /**
     * 检查菜单是否启用
     */
    public boolean isEnabled() {
        return status != null && status == 1;
    }

    /**
     * 是否为目录
     */
    public boolean isDirectory() {
        return type != null && type == 1;
    }

    /**
     * 是否为菜单
     */
    public boolean isMenu() {
        return type != null && type == 2;
    }

    /**
     * 是否为按钮
     */
    public boolean isButton() {
        return type != null && type == 3;
    }

    /**
     * 是否为外链
     */
    public boolean isExternal() {
        return external != null && external == 1;
    }
}
