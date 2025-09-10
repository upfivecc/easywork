package org.easywork.console.infra.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.domain.model.base.TreeNode;

/**
 * 菜单实体
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@TableName(value = "sys_menu")
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuPO extends TreeNode<MenuPO> {

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

}