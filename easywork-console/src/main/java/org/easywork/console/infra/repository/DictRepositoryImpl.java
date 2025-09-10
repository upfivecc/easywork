package org.easywork.console.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.easywork.console.domain.model.Dict;
import org.easywork.console.domain.repository.DictRepository;
import org.easywork.console.infra.repository.base.BaseRepositoryImpl;
import org.easywork.console.infra.repository.converter.DictConverter;
import org.easywork.console.infra.repository.mapper.DictMapper;
import org.easywork.console.infra.repository.po.DictPO;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 字典仓储实现类
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Repository
public class DictRepositoryImpl extends BaseRepositoryImpl<DictMapper, DictPO> implements DictRepository {

    @Override
    public Dict save(Dict dict) {
        DictPO dictPO = DictConverter.INSTANCE.toRepository(dict);
        // 使用基类的通用保存方法
        DictPO savedPO = savePo(dictPO);
        return DictConverter.INSTANCE.toDomain(savedPO);
    }

    @Override
    public Optional<Dict> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        LambdaQueryWrapper<DictPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(DictPO::getId, id);

        DictPO dictPO = super.getOne(queryWrapper);
        return Optional.ofNullable(DictConverter.INSTANCE.toDomain(dictPO));
    }

    @Override
    public Optional<Dict> findByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return Optional.empty();
        }

        LambdaQueryWrapper<DictPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(DictPO::getCode, code);

        DictPO dictPO = super.getOne(queryWrapper);
        return Optional.ofNullable(DictConverter.INSTANCE.toDomain(dictPO));
    }

    @Override
    public List<Dict> findByPage(int page, int size, String keyword) {
        LambdaQueryWrapper<DictPO> queryWrapper = super.queryWrapper();

        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                    .like(DictPO::getName, keyword)
                    .or().like(DictPO::getCode, keyword)
                    .or().like(DictPO::getDescription, keyword)
            );
        }

        queryWrapper.orderByAsc(DictPO::getSort)
                .orderByDesc(DictPO::getCreateTime);

        IPage<DictPO> pageParam = new Page<>(page, size);
        IPage<DictPO> result = super.page(pageParam, queryWrapper);

        return result.getRecords().stream().map(DictConverter.INSTANCE::toDomain).collect(Collectors.toList());
    }

    @Override
    public long count(String keyword) {
        LambdaQueryWrapper<DictPO> queryWrapper = super.queryWrapper();

        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                    .like(DictPO::getName, keyword)
                    .or().like(DictPO::getCode, keyword)
                    .or().like(DictPO::getDescription, keyword)
            );
        }

        return super.count(queryWrapper);
    }

    @Override
    public List<Dict> findAllEnabled() {
        LambdaQueryWrapper<DictPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(DictPO::getStatus, 1)
                .orderByAsc(DictPO::getSort)
                .orderByDesc(DictPO::getCreateTime);

        return super.list(queryWrapper).stream().map(DictConverter.INSTANCE::toDomain).toList();

    }

    @Override
    public boolean existsByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return false;
        }

        LambdaQueryWrapper<DictPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(DictPO::getCode, code);

        return super.count(queryWrapper) > 0;
    }

    @Override
    public void deleteById(Long id) {
        // 使用基类的通用逻辑删除方法
        logicalDeleteById(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }

        // 批量逻辑删除
        LambdaQueryWrapper<DictPO> queryWrapper = Wrappers.lambdaQuery(DictPO.class);
        queryWrapper.in(DictPO::getId, ids);

        DictPO updateEntity = new DictPO();
        updateEntity.setDeleted(1);
        updateEntity.setUpdateTime(LocalDateTime.now());

        super.update(updateEntity, queryWrapper);
    }
}