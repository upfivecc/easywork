package org.easywork.console.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.easywork.console.domain.model.Permission;
import org.easywork.console.domain.repository.PermissionRepository;
import org.easywork.console.infra.repository.base.BaseRepositoryImpl;
import org.easywork.console.infra.repository.converter.PermissionConverter;
import org.easywork.console.infra.repository.mapper.PermissionMapper;
import org.easywork.console.infra.repository.po.PermissionPO;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 权限仓储实现类
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Repository
public class PermissionRepositoryImpl extends BaseRepositoryImpl<PermissionMapper, PermissionPO> implements PermissionRepository {

    @Override
    public Permission save(Permission permission) {
        PermissionPO permissionPO = PermissionConverter.INSTANCE.toRepository(permission);
        if (permissionPO.getId() == null) {
            // 新增操作
            permissionPO.setCreateTime(LocalDateTime.now());
            permissionPO.setDeleted(0);
            permissionPO.setVersion(0);

            // 设置层级和路径
            if (permissionPO.getParentId() != null && permissionPO.getParentId() != 0) {
                Optional<Permission> parent = findById(permissionPO.getParentId());
                if (parent.isPresent()) {
                    Permission parentBO = parent.get();
                    permissionPO.setLevel(parentBO.getLevel() + 1);
                    permissionPO.setPath(parentBO.getPath() + "/" + permissionPO.getId());
                } else {
                    permissionPO.setLevel(1);
                    permissionPO.setPath("/" + permissionPO.getId());
                }
            } else {
                permissionPO.setLevel(1);
                permissionPO.setParentId(0L);
                permissionPO.setPath("/" + permissionPO.getId());
            }

            super.save(permissionPO);

            // 保存后更新路径（因为需要实际的ID）
            if (permissionPO.getParentId() != null && permissionPO.getParentId() != 0) {
                Optional<Permission> parent = findById(permissionPO.getParentId());
                if (parent.isPresent()) {
                    permissionPO.setPath(parent.get().getPath() + "/" + permissionPO.getId());
                    super.updateById(permissionPO);
                }
            } else {
                permissionPO.setPath("/" + permissionPO.getId());
                super.updateById(permissionPO);
            }
        } else {
            // 更新操作
            permissionPO.setUpdateTime(LocalDateTime.now());
            super.updateById(permissionPO);
        }
        return PermissionConverter.INSTANCE.toDomain(permissionPO);
    }

    @Override
    public Optional<Permission> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        LambdaQueryWrapper<PermissionPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PermissionPO::getId, id)
                .eq(PermissionPO::getDeleted, 0);

        PermissionPO permissionPO = super.getOne(queryWrapper);
        return Optional.ofNullable(PermissionConverter.INSTANCE.toDomain(permissionPO));
    }

    @Override
    public Optional<Permission> findByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return Optional.empty();
        }

        LambdaQueryWrapper<PermissionPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PermissionPO::getCode, code)
                .eq(PermissionPO::getDeleted, 0);

        PermissionPO permissionPO = super.getOne(queryWrapper);
        return Optional.ofNullable(PermissionConverter.INSTANCE.toDomain(permissionPO));
    }

    @Override
    public List<Permission> findAllAsTree() {
        LambdaQueryWrapper<PermissionPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PermissionPO::getDeleted, 0)
                .orderByAsc(PermissionPO::getLevel)
                .orderByAsc(PermissionPO::getSort)
                .orderByDesc(PermissionPO::getCreateTime);

        List<PermissionPO> allPermissions = super.list(queryWrapper);
        return buildTree(allPermissions.stream().map(PermissionConverter.INSTANCE::toDomain).toList());
    }

    @Override
    public List<Permission> findByParentId(Long parentId) {
        LambdaQueryWrapper<PermissionPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PermissionPO::getParentId, parentId != null ? parentId : 0)
                .eq(PermissionPO::getDeleted, 0)
                .orderByAsc(PermissionPO::getSort)
                .orderByDesc(PermissionPO::getCreateTime);

        return super.list(queryWrapper).stream().map(PermissionConverter.INSTANCE::toDomain).toList();
    }

    @Override
    public List<Permission> findByUserId(Long userId) {
        if (userId == null) {
            return List.of();
        }

        return baseMapper.selectByUserId(userId).stream().map(PermissionConverter.INSTANCE::toDomain).toList();
    }

    @Override
    public List<Permission> findByRoleId(Long roleId) {
        if (roleId == null) {
            return List.of();
        }

        return baseMapper.selectByRoleId(roleId).stream().map(PermissionConverter.INSTANCE::toDomain).toList();
    }

    @Override
    public List<Permission> findByType(Integer type) {
        if (type == null) {
            return List.of();
        }

        LambdaQueryWrapper<PermissionPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PermissionPO::getType, type)
                .eq(PermissionPO::getDeleted, 0)
                .orderByAsc(PermissionPO::getLevel)
                .orderByAsc(PermissionPO::getSort)
                .orderByDesc(PermissionPO::getCreateTime);

        return super.list(queryWrapper).stream().map(PermissionConverter.INSTANCE::toDomain).toList();
    }

    @Override
    public List<Permission> findAllEnabled() {
        LambdaQueryWrapper<PermissionPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PermissionPO::getStatus, 1)
                .eq(PermissionPO::getDeleted, 0)
                .orderByAsc(PermissionPO::getLevel)
                .orderByAsc(PermissionPO::getSort)
                .orderByDesc(PermissionPO::getCreateTime);

        return super.list(queryWrapper).stream().map(PermissionConverter.INSTANCE::toDomain).toList();
    }

    @Override
    public boolean existsByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return false;
        }

        LambdaQueryWrapper<PermissionPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PermissionPO::getCode, code)
                .eq(PermissionPO::getDeleted, 0);

        return super.count(queryWrapper) > 0;
    }

    @Override
    public void deleteById(Long id) {
        this.logicalDeleteById(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        // 批量逻辑删除
        LambdaQueryWrapper<PermissionPO> queryWrapper = Wrappers.lambdaQuery(PermissionPO.class);
        queryWrapper.in(PermissionPO::getId, ids);

        PermissionPO updateEntity = new PermissionPO();
        updateEntity.setDeleted(1);
        updateEntity.setUpdateTime(LocalDateTime.now());

        super.update(updateEntity, queryWrapper);
    }

    /**
     * 构建权限树形结构
     */
    private List<Permission> buildTree(List<Permission> allPermissions) {
        if (allPermissions == null || allPermissions.isEmpty()) {
            return new ArrayList<>();
        }

        // 按父ID分组
        Map<Long, List<Permission>> groupByParent = allPermissions.stream()
                .collect(Collectors.groupingBy(permission ->
                        permission.getParentId() != null ? permission.getParentId() : 0L));

        // 构建树形结构
        List<Permission> rootPermissions = groupByParent.getOrDefault(0L, new ArrayList<>());

        for (Permission permission : allPermissions) {
            List<Permission> children = groupByParent.get(permission.getId());
            if (children != null && !children.isEmpty()) {
                permission.setChildren(children);
                permission.setIsLeaf(false);
            } else {
                permission.setIsLeaf(true);
            }
        }

        return rootPermissions;
    }
}