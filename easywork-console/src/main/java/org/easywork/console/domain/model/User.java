package org.easywork.console.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.domain.model.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 09:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 性别 1-男 2-女 0-未知
     */
    private Integer gender;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 状态 1-启用 0-禁用
     */
    private Integer status;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户角色列表
     */
    private List<Role> roles;

    /**
     * 用户权限列表
     */
    private List<Permission> permissions;

    /**
     * 检查用户是否启用
     */
    public boolean isEnabled() {
        return status != null && status == 1;
    }

    /**
     * 检查用户是否有指定权限
     */
    public boolean hasPermission(String permissionCode) {
        if (permissions == null || permissions.isEmpty()) {
            return false;
        }
        return permissions.stream().anyMatch(p -> permissionCode.equals(p.getCode()));
    }

    /**
     * 检查用户是否有指定角色
     */
    public boolean hasRole(String roleCode) {
        if (roles == null || roles.isEmpty()) {
            return false;
        }
        return roles.stream().anyMatch(r -> roleCode.equals(r.getCode()));
    }
}
