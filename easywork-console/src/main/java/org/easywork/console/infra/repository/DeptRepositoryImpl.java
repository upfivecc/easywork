package org.easywork.console.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.easywork.console.domain.model.Dept;
import org.easywork.console.domain.repository.DeptRepository;
import org.easywork.console.infra.repository.base.BaseRepositoryImpl;
import org.easywork.console.infra.repository.converter.DeptConverter;
import org.easywork.console.infra.repository.mapper.DeptMapper;
import org.easywork.console.infra.repository.po.DeptPO;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 部门仓储实现
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class DeptRepositoryImpl extends BaseRepositoryImpl<DeptMapper, DeptPO> implements DeptRepository {

    private final DeptMapper deptMapper;

    @Override
    public Dept save(Dept dept) {
        DeptPO deptPO = DeptConverter.INSTANCE.toRepository(dept);
        DeptPO savedPo = savePo(deptPO);
        return DeptConverter.INSTANCE.toDomain(savedPo);
    }

    @Override
    public Optional<Dept> findById(Long id) {
        DeptPO deptPO = deptMapper.selectById(id);
        return Optional.ofNullable(deptPO)
                .map(DeptConverter.INSTANCE::toDomain);
    }

    @Override
    public Optional<Dept> findByCode(String code) {
        LambdaQueryWrapper<DeptPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeptPO::getCode, code)
                .eq(DeptPO::getDeleted, 0)
                .last("LIMIT 1");
        DeptPO deptPO = deptMapper.selectOne(queryWrapper);
        return Optional.ofNullable(deptPO)
                .map(DeptConverter.INSTANCE::toDomain);
    }

    @Override
    public List<Dept> findAllAsTree() {
        LambdaQueryWrapper<DeptPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeptPO::getStatus, 1)
                .eq(DeptPO::getDeleted, 0)
                .orderByAsc(DeptPO::getLevel, DeptPO::getSort);
        List<DeptPO> deptPOs = deptMapper.selectList(queryWrapper);
        List<Dept> depts = deptPOs.stream()
                .map(DeptConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());

        return buildDeptTree(depts, 0L);
    }

    @Override
    public List<Dept> findByParentId(Long parentId) {
        LambdaQueryWrapper<DeptPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeptPO::getParentId, parentId)
                .eq(DeptPO::getDeleted, 0)
                .orderByAsc(DeptPO::getSort);
        List<DeptPO> deptPOs = deptMapper.selectList(queryWrapper);
        return deptPOs.stream()
                .map(DeptConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dept> findByType(Integer type) {
        LambdaQueryWrapper<DeptPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeptPO::getType, type)
                .eq(DeptPO::getDeleted, 0)
                .orderByAsc(DeptPO::getLevel, DeptPO::getSort);
        List<DeptPO> deptPOs = deptMapper.selectList(queryWrapper);
        return deptPOs.stream()
                .map(DeptConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dept> findAllEnabled() {
        LambdaQueryWrapper<DeptPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeptPO::getStatus, 1)
                .eq(DeptPO::getDeleted, 0)
                .orderByAsc(DeptPO::getLevel, DeptPO::getSort);
        List<DeptPO> deptPOs = deptMapper.selectList(queryWrapper);
        return deptPOs.stream()
                .map(DeptConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dept> findByLeaderId(Long leaderId) {
        LambdaQueryWrapper<DeptPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeptPO::getLeaderId, leaderId)
                .eq(DeptPO::getDeleted, 0);
        List<DeptPO> deptPOs = deptMapper.selectList(queryWrapper);
        return deptPOs.stream()
                .map(DeptConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCode(String code) {
        LambdaQueryWrapper<DeptPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeptPO::getCode, code)
                .eq(DeptPO::getDeleted, 0);
        return deptMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public void deleteById(Long id) {
        // 逻辑删除
        this.logicalDeleteById(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        // 批量逻辑删除
        LambdaQueryWrapper<DeptPO> queryWrapper = Wrappers.lambdaQuery(DeptPO.class);
        queryWrapper.in(DeptPO::getId, ids);

        DeptPO updateEntity = new DeptPO();
        updateEntity.setDeleted(1);
        updateEntity.setUpdateTime(LocalDateTime.now());

        super.update(updateEntity, queryWrapper);
    }

    @Override
    public List<Long> findAllChildrenIds(Long deptId) {
        List<DeptPO> children = deptMapper.selectAllChildren(deptId);
        return children.stream()
                .map(DeptPO::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dept> findByDataScope(Long userId, Integer dataScope) {
        // 根据数据权限范围查询部门
        // 这里需要根据具体的业务逻辑实现
        // 暂时返回所有启用的部门
        return findAllEnabled();
    }

    /**
     * 构建部门树形结构
     */
    private List<Dept> buildDeptTree(List<Dept> flatList, Long rootId) {
        if (CollectionUtils.isEmpty(flatList)) {
            return List.of();
        }

        // 按parentId分组
        Map<Long, List<Dept>> parentIdMap = flatList.stream()
                .collect(Collectors.groupingBy(
                        dept -> dept.getParentId() != null ? dept.getParentId() : 0L
                ));

        // 递归构建树形结构
        List<Dept> rootNodes = parentIdMap.get(rootId);
        if (CollectionUtils.isEmpty(rootNodes)) {
            return List.of();
        }

        for (Dept rootNode : rootNodes) {
            buildChildren(rootNode, parentIdMap);
        }

        // 设置叶子节点标识
        setLeafFlag(rootNodes);

        return rootNodes;
    }

    /**
     * 递归构建子节点
     */
    private void buildChildren(Dept parent, Map<Long, List<Dept>> parentIdMap) {
        List<Dept> children = parentIdMap.get(parent.getId());
        if (!CollectionUtils.isEmpty(children)) {
            // 按排序字段排序
            children.sort((a, b) -> {
                Integer sortA = a.getSort() != null ? a.getSort() : 0;
                Integer sortB = b.getSort() != null ? b.getSort() : 0;
                return sortA.compareTo(sortB);
            });

            parent.setChildren(children);

            // 递归处理子节点
            for (Dept child : children) {
                buildChildren(child, parentIdMap);
            }
        }
    }

    /**
     * 设置叶子节点标识
     */
    private void setLeafFlag(List<Dept> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }

        for (Dept node : nodes) {
            node.setIsLeaf(CollectionUtils.isEmpty(node.getChildren()));

            if (!CollectionUtils.isEmpty(node.getChildren())) {
                setLeafFlag(node.getChildren());
            }
        }
    }
}