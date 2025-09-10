package org.easywork.console.domain.model;

import lombok.Data;

import java.util.List;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 09:54
 */
@Data
public class Role {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色代码
     */
    private String code;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 状态 1-启用 0-禁用
     */
    private Integer status;

    /**
     * 数据范围 1-全部数据 2-本部门及以下数据 3-本部门数据 4-仅本人数据 5-自定义数据
     */
    private Integer dataScope;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色权限列表
     */
    private List<Permission> permissions;

    /**
     * 检查角色是否启用
     */
    public boolean isEnabled() {
        return status != null && status == 1;
    }

    /**
     * 检查角色是否有指定权限
     */
    public boolean hasPermission(String permissionCode) {
        if (permissions == null || permissions.isEmpty()) {
            return false;
        }
        return permissions.stream().anyMatch(p -> permissionCode.equals(p.getCode()));
    }
}
