package org.easywork.console.infra.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.easywork.console.domain.model.base.TreeNode;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 树形结构工具类
 * 基于 code 进行树形结构构建，避免数据迁移时的ID依赖问题
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
     * @param rootCode 根节点编码，通常为null或空字符串表示根节点
     * @param <T>      树节点类型，必须继承 TreeNode
     * @return 树形结构列表
     */
    public static <T extends TreeNode<T>> List<T> buildTree(List<T> flatList, String rootCode) {
        if (CollectionUtils.isEmpty(flatList)) {
            return new ArrayList<>();
        }

        // 1. 先计算每个节点的层级和路径
        calculateLevelAndPath(flatList);

        // 2. 按parentCode分组
        Map<String, List<T>> parentCodeMap = flatList.stream()
                .collect(Collectors.groupingBy(
                        node -> {
                            String parentCode = node.getParentCode();
                            return (parentCode == null || parentCode.isEmpty() || "0".equals(parentCode)) ? "ROOT" : parentCode;
                        }
                ));

        // 3. 获取根节点
        String rootKey = (rootCode == null || rootCode.isEmpty() || "0".equals(rootCode)) ? "ROOT" : rootCode;
        List<T> rootNodes = parentCodeMap.get(rootKey);
        if (CollectionUtils.isEmpty(rootNodes)) {
            return new ArrayList<>();
        }

        // 4. 递归构建树形结构
        for (T rootNode : rootNodes) {
            buildChildren(rootNode, parentCodeMap);
        }

        // 5. 设置叶子节点标识
        setLeafFlag(rootNodes);

        return rootNodes;
    }

    /**
     * 兼容原有基于ID的方法
     */
    @Deprecated
    public static <T extends TreeNode<T>> List<T> buildTree(List<T> flatList, Long rootId) {
        return buildTree(flatList, (String) null);
    }

    /**
     * 计算节点层级和路径
     */
    private static <T extends TreeNode<T>> void calculateLevelAndPath(List<T> flatList) {
        Map<String, T> nodeMap = flatList.stream()
                .collect(Collectors.toMap(TreeNode::getCode, node -> node));

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
    private static <T extends TreeNode<T>> int calculateLevel(T node, Map<String, T> nodeMap, int currentLevel) {
        String parentCode = node.getParentCode();
        if (parentCode == null || parentCode.isEmpty() || "0".equals(parentCode)) {
            return currentLevel;
        }

        T parent = nodeMap.get(parentCode);
        if (parent == null) {
            return currentLevel;
        }

        return calculateLevel(parent, nodeMap, currentLevel + 1);
    }

    /**
     * 计算节点路径
     * 路径格式: "父节点code/父节点code/当前节点code"
     * 示例:
     * - 根节点: "company"
     * - 二级节点: "company/dept1"
     * - 三级节点: "company/dept1/group1"
     */
    private static <T extends TreeNode<T>> String calculatePath(T node, Map<String, T> nodeMap) {
        List<String> pathCodes = new ArrayList<>();
        buildPathCodes(node, nodeMap, pathCodes);

        // 反转路径，使其从根到叶子
        List<String> reversedPath = new ArrayList<>();
        for (int i = pathCodes.size() - 1; i >= 0; i--) {
            reversedPath.add(pathCodes.get(i));
        }

        return String.join("/", reversedPath);
    }

    /**
     * 构建路径Code列表
     */
    private static <T extends TreeNode<T>> void buildPathCodes(T node, Map<String, T> nodeMap, List<String> pathCodes) {
        pathCodes.add(node.getCode());

        String parentCode = node.getParentCode();
        if (StringUtils.hasText(parentCode) && !"0".equals(parentCode)) {
            T parent = nodeMap.get(parentCode);
            if (parent != null) {
                buildPathCodes(parent, nodeMap, pathCodes);
            }
        }
    }

    /**
     * 递归构建子节点
     */
    private static <T extends TreeNode<T>> void buildChildren(T parent, Map<String, List<T>> parentCodeMap) {
        List<T> children = parentCodeMap.get(parent.getCode());
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
                buildChildren(child, parentCodeMap);
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
     * 根据Code查找节点（在树形结构中）
     */
    public static <T extends TreeNode<T>> T findNodeByCode(List<T> treeList, String code) {
        if (CollectionUtils.isEmpty(treeList) || !StringUtils.hasText(code)) {
            return null;
        }

        for (T node : treeList) {
            T found = findNodeByCodeRecursive(node, code);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    /**
     * 递归查找节点
     */
    private static <T extends TreeNode<T>> T findNodeByCodeRecursive(T node, String code) {
        if (code.equals(node.getCode())) {
            return node;
        }

        if (!CollectionUtils.isEmpty(node.getChildren())) {
            for (T child : node.getChildren()) {
                T found = findNodeByCodeRecursive(child, code);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    /**
     * 兼容原有基于ID的查找方法
     */
    @Deprecated
    public static <T extends TreeNode<T>> T findNodeById(List<T> treeList, Long id) {
        // 由于TreeNode基类已经不包含id字段，这个方法需要重新设计
        log.warn("findNodeById method is deprecated, please use findNodeByCode instead");
        return null;
    }

    /**
     * 获取节点的所有子节点Code（包括子节点的子节点）
     */
    public static <T extends TreeNode<T>> List<String> getAllChildrenCodes(T node) {
        List<String> childrenCodes = new ArrayList<>();
        if (node == null) {
            return childrenCodes;
        }

        collectChildrenCodes(node, childrenCodes);
        return childrenCodes;
    }

    /**
     * 递归收集子节点Code
     */
    private static <T extends TreeNode<T>> void collectChildrenCodes(T node, List<String> childrenCodes) {
        if (!CollectionUtils.isEmpty(node.getChildren())) {
            for (T child : node.getChildren()) {
                childrenCodes.add(child.getCode());
                collectChildrenCodes(child, childrenCodes);
            }
        }
    }

    /**
     * 获取从根节点到指定节点的路径
     */
    public static <T extends TreeNode<T>> List<T> getPathToNode(List<T> treeList, String targetCode) {
        List<T> path = new ArrayList<>();
        if (CollectionUtils.isEmpty(treeList) || !StringUtils.hasText(targetCode)) {
            return path;
        }

        for (T node : treeList) {
            if (findPathToNodeRecursive(node, targetCode, path)) {
                return path;
            }
        }

        return new ArrayList<>();
    }

    /**
     * 递归查找到指定节点的路径
     */
    private static <T extends TreeNode<T>> boolean findPathToNodeRecursive(T node, String targetCode, List<T> path) {
        path.add(node);

        if (targetCode.equals(node.getCode())) {
            return true;
        }

        if (!CollectionUtils.isEmpty(node.getChildren())) {
            for (T child : node.getChildren()) {
                if (findPathToNodeRecursive(child, targetCode, path)) {
                    return true;
                }
            }
        }

        path.remove(path.size() - 1);
        return false;
    }
}
