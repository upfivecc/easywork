package org.easywork.console.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.easywork.common.rest.result.PageInfo;
import org.easywork.console.domain.model.Role;
import org.easywork.console.domain.model.dto.RoleQuery;
import org.easywork.console.domain.repository.RoleRepository;
import org.easywork.console.infra.repository.base.BaseRepositoryImpl;
import org.easywork.console.infra.repository.converter.RoleConverter;
import org.easywork.console.infra.repository.mapper.RoleMapper;
import org.easywork.console.infra.repository.po.RolePO;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 角色仓储实现类
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Repository
public class RoleRepositoryImpl extends BaseRepositoryImpl<RoleMapper, RolePO, Role, RoleQuery> implements RoleRepository {

    @Override
    public Role persist(Role role) {
        RolePO rolePO = RoleConverter.INSTANCE.toRepository(role);
        RolePO savedPo = savePo(rolePO);
        return RoleConverter.INSTANCE.toDomain(savedPo);
    }

    @Override
    public Optional<Role> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        LambdaQueryWrapper<RolePO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(RolePO::getId, id);

        RolePO rolePO = super.getOne(queryWrapper);
        return Optional.ofNullable(RoleConverter.INSTANCE.toDomain(rolePO));
    }

    @Override
    public Optional<Role> findByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return Optional.empty();
        }

        LambdaQueryWrapper<RolePO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(RolePO::getCode, code);

        RolePO rolePO = super.getOne(queryWrapper);
        return Optional.ofNullable(RoleConverter.INSTANCE.toDomain(rolePO));
    }

    @Override
    public List<Role> findByPage(int page, int size, String keyword) {
        LambdaQueryWrapper<RolePO> queryWrapper = super.queryWrapper();

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
        LambdaQueryWrapper<RolePO> queryWrapper = super.queryWrapper();

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
        LambdaQueryWrapper<RolePO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(RolePO::getStatus, 1)
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
    public PageInfo<Role> findByPage(RoleQuery query) {
        LambdaQueryWrapper<RolePO> queryWrapper = super.queryWrapper();
        
        // 关键字搜索：角色名称、角色代码、角色描述
        String keyword = query.getKeyword();
        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                    .like(RolePO::getName, keyword)
                    .or().like(RolePO::getCode, keyword)
                    .or().like(RolePO::getDescription, keyword)
            );
        }
        
        // 按角色状态过滤（可选）
        Integer status = query.getStatus();
        if (status != null) {
            queryWrapper.eq(RolePO::getStatus, status);
        }
        
        // 按数据权限范围过滤（可选）
        Integer dataScope = query.getDataScope();
        if (dataScope != null) {
            queryWrapper.eq(RolePO::getDataScope, dataScope);
        }
        
        // 按排序号升序，再按创建时间降序
        queryWrapper.orderByAsc(RolePO::getSort)
                    .orderByDesc(RolePO::getCreateTime);
        
        // 分页查询
        IPage<RolePO> pageParam = new Page<>(query.getPageNum(), query.getPageSize());
        IPage<RolePO> result = super.page(pageParam, queryWrapper);
        
        // 转换为域对象
        List<Role> roles = result.getRecords().stream()
                .map(RoleConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());
        
        // 构建分页结果
        return PageInfo.<Role>builder()
                .page(query.getPageNum())
                .pageSize(query.getPageSize())
                .total(result.getTotal())
                .records(roles)
                .build();
    }
}