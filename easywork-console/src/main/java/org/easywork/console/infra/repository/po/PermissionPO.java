package org.easywork.console.infra.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.infra.repository.po.base.BasePO;

/**
 * 权限实体
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
     * 父节点ID
     */
    private Long parentId;

    /**
     * 节点层级
     */
    private Integer level;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 路径（用于快速查询子树）
     */
    private String path;

}