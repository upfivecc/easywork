package org.easywork.console.infra.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.easywork.console.domain.model.base.TreeNode;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 树形结构工具类
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Slf4j
public class TreeUtils {

    /**
     * 构建树形结构
     *
     * @param flatList 扁平化列表
     * @param rootId   根节点ID，通常为0或null
     * @param <T>      树节点类型，必须继承TreeNode
     * @return 树形结构列表
     */
    public static <T extends TreeNode<T>> List<T> buildTree(List<T> flatList, Long rootId) {
        if (CollectionUtils.isEmpty(flatList)) {
            return new ArrayList<>();
        }

        // 设置根节点ID，默认为0
        if (rootId == null) {
            rootId = 0L;
        }

        // 1. 先计算每个节点的层级和路径
        calculateLevelAndPath(flatList);

        // 2. 按parentId分组
        Map<Long, List<T>> parentIdMap = flatList.stream()
                .collect(Collectors.groupingBy(
                        node -> node.getParentId() != null ? node.getParentId() : 0L
                ));

        // 3. 递归构建树形结构
        List<T> rootNodes = parentIdMap.get(rootId);
        if (CollectionUtils.isEmpty(rootNodes)) {
            return new ArrayList<>();
        }

        for (T rootNode : rootNodes) {
            buildChildren(rootNode, parentIdMap);
        }

        // 4. 设置叶子节点标识
        setLeafFlag(rootNodes);

        return rootNodes;
    }

    /**
     * 计算节点层级和路径
     */
    private static <T extends TreeNode<T>> void calculateLevelAndPath(List<T> flatList) {
        Map<Long, T> nodeMap = flatList.stream()
                .collect(Collectors.toMap(TreeNode::getId, node -> node));

        for (T node : flatList) {
            // 计算层级
            int level = calculateLevel(node, nodeMap, 1);
            node.setLevel(level);

            // 计算路径
            String path = calculatePath(node, nodeMap);
            node.setPath(path);
        }
    }

    /**
     * 计算节点层级
     */
    private static <T extends TreeNode<T>> int calculateLevel(T node, Map<Long, T> nodeMap, int currentLevel) {
        if (node.getParentId() == null || node.getParentId() == 0L) {
            return currentLevel;
        }

        T parent = nodeMap.get(node.getParentId());
        if (parent == null) {
            return currentLevel;
        }

        return calculateLevel(parent, nodeMap, currentLevel + 1);
    }

    /**
     * 计算节点路径
     * 路径格式: "父节点ID/父节点ID/当前节点ID"
     * 示例:
     * - 根节点: "1"
     * - 二级节点: "1/2"
     * - 三级节点: "1/2/3"
     */
    private static <T extends TreeNode<T>> String calculatePath(T node, Map<Long, T> nodeMap) {
        List<Long> pathIds = new ArrayList<>();
        buildPathIds(node, nodeMap, pathIds);

        // 反转路径，使其从根到叶子
        List<Long> reversedPath = new ArrayList<>();
        for (int i = pathIds.size() - 1; i >= 0; i--) {
            reversedPath.add(pathIds.get(i));
        }

        return reversedPath.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("/"));
    }

    /**
     * 构建路径ID列表
     */
    private static <T extends TreeNode<T>> void buildPathIds(T node, Map<Long, T> nodeMap, List<Long> pathIds) {
        pathIds.add(node.getId());

        if (node.getParentId() != null && node.getParentId() != 0L) {
            T parent = nodeMap.get(node.getParentId());
            if (parent != null) {
                buildPathIds(parent, nodeMap, pathIds);
            }
        }
    }

    /**
     * 递归构建子节点
     */
    private static <T extends TreeNode<T>> void buildChildren(T parent, Map<Long, List<T>> parentIdMap) {
        List<T> children = parentIdMap.get(parent.getId());
        if (!CollectionUtils.isEmpty(children)) {
            // 按排序字段排序
            children.sort((a, b) -> {
                Integer sortA = a.getSort() != null ? a.getSort() : 0;
                Integer sortB = b.getSort() != null ? b.getSort() : 0;
                return sortA.compareTo(sortB);
            });

            parent.setChildren(children);

            // 递归处理子节点
            for (T child : children) {
                buildChildren(child, parentIdMap);
            }
        }
    }

    /**
     * 设置叶子节点标识
     */
    private static <T extends TreeNode<T>> void setLeafFlag(List<T> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }

        for (T node : nodes) {
            node.setIsLeaf(CollectionUtils.isEmpty(node.getChildren()));

            if (!CollectionUtils.isEmpty(node.getChildren())) {
                setLeafFlag(node.getChildren());
            }
        }
    }

    /**
     * 扁平化树形结构
     *
     * @param treeList 树形结构列表
     * @return 扁平化列表
     */
    public static <T extends TreeNode<T>> List<T> flattenTree(List<T> treeList) {
        List<T> flatList = new ArrayList<>();
        if (CollectionUtils.isEmpty(treeList)) {
            return flatList;
        }

        for (T node : treeList) {
            flattenNode(node, flatList);
        }

        return flatList;
    }

    /**
     * 递归扁平化节点
     */
    private static <T extends TreeNode<T>> void flattenNode(T node, List<T> flatList) {
        flatList.add(node);

        if (!CollectionUtils.isEmpty(node.getChildren())) {
            for (T child : node.getChildren()) {
                flattenNode(child, flatList);
            }
        }
    }

    /**
     * 根据ID查找节点（在树形结构中）
     */
    public static <T extends TreeNode<T>> T findNodeById(List<T> treeList, Long id) {
        if (CollectionUtils.isEmpty(treeList) || id == null) {
            return null;
        }

        for (T node : treeList) {
            T found = findNodeByIdRecursive(node, id);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    /**
     * 递归查找节点
     */
    private static <T extends TreeNode<T>> T findNodeByIdRecursive(T node, Long id) {
        if (id.equals(node.getId())) {
            return node;
        }

        if (!CollectionUtils.isEmpty(node.getChildren())) {
            for (T child : node.getChildren()) {
                T found = findNodeByIdRecursive(child, id);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    /**
     * 获取节点的所有子节点ID（包括子节点的子节点）
     */
    public static <T extends TreeNode<T>> List<Long> getAllChildrenIds(T node) {
        List<Long> childrenIds = new ArrayList<>();
        if (node == null) {
            return childrenIds;
        }

        collectChildrenIds(node, childrenIds);
        return childrenIds;
    }

    /**
     * 递归收集子节点ID
     */
    private static <T extends TreeNode<T>> void collectChildrenIds(T node, List<Long> childrenIds) {
        if (!CollectionUtils.isEmpty(node.getChildren())) {
            for (T child : node.getChildren()) {
                childrenIds.add(child.getId());
                collectChildrenIds(child, childrenIds);
            }
        }
    }

    /**
     * 获取从根节点到指定节点的路径
     */
    public static <T extends TreeNode<T>> List<T> getPathToNode(List<T> treeList, Long targetId) {
        List<T> path = new ArrayList<>();
        if (CollectionUtils.isEmpty(treeList) || targetId == null) {
            return path;
        }

        for (T node : treeList) {
            if (findPathToNodeRecursive(node, targetId, path)) {
                return path;
            }
        }

        return new ArrayList<>();
    }

    /**
     * 递归查找到指定节点的路径
     */
    private static <T extends TreeNode<T>> boolean findPathToNodeRecursive(T node, Long targetId, List<T> path) {
        path.add(node);

        if (targetId.equals(node.getId())) {
            return true;
        }

        if (!CollectionUtils.isEmpty(node.getChildren())) {
            for (T child : node.getChildren()) {
                if (findPathToNodeRecursive(child, targetId, path)) {
                    return true;
                }
            }
        }

        path.remove(path.size() - 1);
        return false;
    }
}
