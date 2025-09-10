package org.easywork.console.infra.repository.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.infra.repository.po.base.BasePO;

/**
 * 菜单实体
 * 基于 code 进行树形结构关联
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@TableName(value = "sys_menu")
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuPO extends BasePO {

    /**
     * 菜单名称
     */
    @TableField("name")
    private String name;

    /**
     * 菜单代码
     */
    @TableField("code")
    private String code;

    /**
     * 菜单类型 1-目录 2-菜单 3-按钮
     */
    @TableField("type")
    private Integer type;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 父菜单编码
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