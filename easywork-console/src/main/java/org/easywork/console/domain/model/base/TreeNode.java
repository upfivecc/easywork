package org.easywork.console.domain.model.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 树形结构节点基类
 * 基于code进行树形关联，避免数据迁移时的ID依赖问题
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class TreeNode<T extends TreeNode<T>> extends BaseEntity {

    /**
     * 节点编码（唯一标识）
     */
    private String code;

    /**
     * 父节点编码
     */
    private String parentCode;

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
     * 格式: "父节点code/父节点code/当前节点code"
     * 示例: "company/dept1/group1"
     */
    private String path;

    /**
     * 子节点列表
     */
    private List<T> children;

    /**
     * 是否为叶子节点
     */
    private Boolean isLeaf;

    /**
     * 是否为根节点
     */
    public boolean isRoot() {
        return parentCode == null || parentCode.isEmpty() || "0".equals(parentCode);
    }

    /**
     * 获取层级深度
     */
    public int getDepth() {
        if (path == null || path.isEmpty()) {
            return 1;
        }
        return path.split("/").length;
    }
}