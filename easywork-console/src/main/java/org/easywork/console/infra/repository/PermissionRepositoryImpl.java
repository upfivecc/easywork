package org.easywork.console.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.easywork.common.rest.result.PageInfo;
import org.easywork.console.domain.model.Permission;
import org.easywork.console.domain.model.dto.PermissionQuery;
import org.easywork.console.domain.repository.PermissionRepository;
import org.easywork.console.infra.common.utils.TreeUtils;
import org.easywork.console.infra.repository.base.BaseRepositoryImpl;
import org.easywork.console.infra.repository.converter.PermissionConverter;
import org.easywork.console.infra.repository.mapper.PermissionMapper;
import org.easywork.console.infra.repository.po.PermissionPO;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
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

        LambdaQueryWrapper<PermissionPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(PermissionPO::getId, id);

        PermissionPO permissionPO = super.getOne(queryWrapper);
        return Optional.ofNullable(PermissionConverter.INSTANCE.toDomain(permissionPO));
    }

    @Override
    public Optional<Permission> findByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return Optional.empty();
        }

        LambdaQueryWrapper<PermissionPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(PermissionPO::getCode, code);

        PermissionPO permissionPO = super.getOne(queryWrapper);
        return Optional.ofNullable(PermissionConverter.INSTANCE.toDomain(permissionPO));
    }

    @Override
    public List<Permission> findAllAsTree() {
        LambdaQueryWrapper<PermissionPO> queryWrapper = super.queryWrapper();
        queryWrapper.orderByAsc(PermissionPO::getLevel)
                .orderByAsc(PermissionPO::getSort)
                .orderByDesc(PermissionPO::getCreateTime);

        List<PermissionPO> allPermissions = super.list(queryWrapper);
        return TreeUtils.buildTree(allPermissions.stream().map(PermissionConverter.INSTANCE::toDomain).toList(), 0L);
    }

    @Override
    public List<Permission> findByParentId(Long parentId) {
        LambdaQueryWrapper<PermissionPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(PermissionPO::getParentId, parentId != null ? parentId : 0)
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

        LambdaQueryWrapper<PermissionPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(PermissionPO::getType, type)
                .orderByAsc(PermissionPO::getLevel)
                .orderByAsc(PermissionPO::getSort)
                .orderByDesc(PermissionPO::getCreateTime);

        return super.list(queryWrapper).stream().map(PermissionConverter.INSTANCE::toDomain).toList();
    }

    @Override
    public List<Permission> findAllEnabled() {
        LambdaQueryWrapper<PermissionPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(PermissionPO::getStatus, 1)
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

        LambdaQueryWrapper<PermissionPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(PermissionPO::getCode, code);

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

    @Override
    public PageInfo<Permission> findByPage(PermissionQuery query) {
        LambdaQueryWrapper<PermissionPO> queryWrapper = super.queryWrapper();
        
        // 关键字搜索：权限名称、权限代码、资源路径
        String keyword = query.getKeyword();
        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                    .like(PermissionPO::getName, keyword)
                    .or().like(PermissionPO::getCode, keyword)
                    .or().like(PermissionPO::getResource, keyword)
            );
        }
        
        // 按权限类型过滤（可选）
        Integer type = query.getType();
        if (type != null) {
            queryWrapper.eq(PermissionPO::getType, type);
        }
        
        // 按权限状态过滤（可选）
        Integer status = query.getStatus();
        if (status != null) {
            queryWrapper.eq(PermissionPO::getStatus, status);
        }
        
        // 按HTTP方法过滤（可选）
        String method = query.getMethod();
        if (StringUtils.hasText(method)) {
            queryWrapper.eq(PermissionPO::getMethod, method);
        }
        
        // 按层级和排序号排序，再按创建时间降序
        queryWrapper.orderByAsc(PermissionPO::getLevel, PermissionPO::getSort)
                    .orderByDesc(PermissionPO::getCreateTime);
        
        // 分页查询
        IPage<PermissionPO> pageParam = new Page<>(query.getPageNum(), query.getPageSize());
        IPage<PermissionPO> result = super.page(pageParam, queryWrapper);
        
        // 转换为域对象
        List<Permission> permissions = result.getRecords().stream()
                .map(PermissionConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());
        
        // 构建分页结果
        return PageInfo.<Permission>builder()
                .page(query.getPageNum())
                .pageSize(query.getPageSize())
                .total(result.getTotal())
                .records(permissions)
                .build();
    }
}