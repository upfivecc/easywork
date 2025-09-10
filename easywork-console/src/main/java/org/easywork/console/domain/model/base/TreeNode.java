package org.easywork.console.domain.model.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 树形结构节点基类
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class TreeNode<T extends TreeNode<T>> extends BaseEntity {

    /**
     * 节点 ID
     */
    private Long id;

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

    /**
     * 子节点列表
     */
    private List<T> children;

    /**
     * 是否为叶子节点
     */
    private Boolean isLeaf;
}