package org.easywork.console.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.domain.model.base.TreeNode;

import java.util.List;

/**
 * 部门实体
 * 基于 code 进行树形结构关联
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Dept extends TreeNode<Dept> {

    /**
     * 父部门编码
     */
    private String parentCode;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门编码
     */
    private String code;

    /**
     * 部门类型 1-公司 2-部门 3-小组
     */
    private Integer type;

    /**
     * 部门负责人ID
     */
    private Long leaderId;

    /**
     * 部门负责人姓名
     */
    private String leaderName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门地址
     */
    private String address;

    /**
     * 状态 1-启用 0-禁用
     */
    private Integer status;

    /**
     * 子部门列表
     */
    private List<Dept> children;

    /**
     * 是否为叶子节点
     */
    private Boolean isLeaf;

    /**
     * 备注
     */
    private String remark;

    /**
     * 检查部门是否启用
     */
    public boolean isEnabled() {
        return status != null && status == 1;
    }

    /**
     * 是否为公司级部门
     */
    public boolean isCompany() {
        return type != null && type == 1;
    }

    /**
     * 是否为部门级
     */
    public boolean isDepartment() {
        return type != null && type == 2;
    }

    /**
     * 是否为小组级
     */
    public boolean isGroup() {
        return type != null && type == 3;
    }
}