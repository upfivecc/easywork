package org.easywork.console.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.easywork.console.domain.model.Role;
import org.easywork.console.domain.repository.RoleRepository;
import org.easywork.console.infra.repository.converter.RoleConverter;
import org.easywork.console.infra.repository.mapper.RoleMapper;
import org.easywork.console.infra.repository.po.RolePO;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 角色仓储实现类
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Repository
public class RoleRepositoryImpl extends BaseRepositoryImpl<RoleMapper, RolePO> implements RoleRepository {

    @Override
    public Role save(Role role) {
        RolePO rolePO = RoleConverter.INSTANCE.toRepository(role);
        RolePO savedPo = savePo(rolePO);
        return RoleConverter.INSTANCE.toDomain(savedPo);
    }

    @Override
    public Optional<Role> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        LambdaQueryWrapper<RolePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePO::getId, id)
                .eq(RolePO::getDeleted, 0);

        RolePO rolePO = super.getOne(queryWrapper);
        return Optional.ofNullable(RoleConverter.INSTANCE.toDomain(rolePO));
    }

    @Override
    public Optional<Role> findByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return Optional.empty();
        }

        LambdaQueryWrapper<RolePO> queryWrapper = Wrappers.lambdaQuery(RolePO.class);
        queryWrapper.eq(RolePO::getCode, code)
                .eq(RolePO::getDeleted, 0);

        RolePO rolePO = super.getOne(queryWrapper);
        return Optional.ofNullable(RoleConverter.INSTANCE.toDomain(rolePO));
    }

    @Override
    public List<Role> findByPage(int page, int size, String keyword) {
        LambdaQueryWrapper<RolePO> queryWrapper = Wrappers.lambdaQuery(RolePO.class);
        queryWrapper.eq(RolePO::getDeleted, 0);

        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                    .like(RolePO::getName, keyword)
                    .or().like(RolePO::getCode, keyword)
                    .or().like(RolePO::getDescription, keyword)
            );
        }

        queryWrapper.orderByAsc(RolePO::getSort)
                .orderByDesc(RolePO::getCreateTime);

        IPage<RolePO> pageParam = new Page<>(page, size);
        IPage<RolePO> result = super.page(pageParam, queryWrapper);

        return result.getRecords().stream().map(RoleConverter.INSTANCE::toDomain).toList();
    }

    @Override
    public long count(String keyword) {
        LambdaQueryWrapper<RolePO> queryWrapper = Wrappers.lambdaQuery(RolePO.class);
        queryWrapper.eq(RolePO::getDeleted, 0);

        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                    .like(RolePO::getName, keyword)
                    .or().like(RolePO::getCode, keyword)
                    .or().like(RolePO::getDescription, keyword)
            );
        }

        return super.count(queryWrapper);
    }

    @Override
    public List<Role> findAllEnabled() {
        LambdaQueryWrapper<RolePO> queryWrapper = Wrappers.lambdaQuery(RolePO.class);
        queryWrapper.eq(RolePO::getStatus, 1)
                .eq(RolePO::getDeleted, 0)
                .orderByAsc(RolePO::getSort)
                .orderByDesc(RolePO::getCreateTime);

        return super.list(queryWrapper).stream().map(RoleConverter.INSTANCE::toDomain).toList();
    }

    @Override
    public List<Role> findByUserId(Long userId) {
        if (userId == null) {
            return List.of();
        }

        return baseMapper.selectByUserId(userId).stream().map(RoleConverter.INSTANCE::toDomain).toList();
    }

    @Override
    public boolean existsByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return false;
        }

        LambdaQueryWrapper<RolePO> queryWrapper = Wrappers.lambdaQuery(RolePO.class);
        queryWrapper.eq(RolePO::getCode, code)
                .eq(RolePO::getDeleted, 0);

        return super.count(queryWrapper) > 0;
    }

    @Override
    public void deleteById(Long id) {
        this.logicalDeleteById(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }

        // 批量逻辑删除
        LambdaQueryWrapper<RolePO> queryWrapper = Wrappers.lambdaQuery(RolePO.class);
        queryWrapper.in(RolePO::getId, ids);

        RolePO updateEntity = new RolePO();
        updateEntity.setDeleted(1);
        updateEntity.setUpdateTime(LocalDateTime.now());

        super.update(updateEntity, queryWrapper);
    }
}