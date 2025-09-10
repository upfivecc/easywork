package org.easywork.console.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.easywork.console.domain.model.Dict;
import org.easywork.console.domain.model.dto.DictQuery;
import org.easywork.console.domain.repository.DictRepository;
import org.easywork.console.infra.repository.base.BaseRepositoryImpl;
import org.easywork.console.infra.repository.converter.DictConverter;
import org.easywork.console.infra.repository.mapper.DictMapper;
import org.easywork.console.infra.repository.po.DictPO;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * 字典仓储实现类
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Repository
public class DictRepositoryImpl extends BaseRepositoryImpl<DictMapper, DictPO, Dict, DictQuery> implements DictRepository {

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
    protected void buildQuery(LambdaQueryWrapper<DictPO> queryWrapper, DictQuery query) {
        // 关键字搜索：字典名称、字典编码、字典描述
        String keyword = query.getKeyword();
        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                    .like(DictPO::getName, keyword)
                    .or().like(DictPO::getCode, keyword)
                    .or().like(DictPO::getDescription, keyword)
            );
        }

        // 按字典状态过滤（可选）
        Integer status = query.getStatus();
        if (status != null) {
            queryWrapper.eq(DictPO::getStatus, status);
        }

        // 按排序号升序，再按创建时间降序
        queryWrapper.orderByAsc(DictPO::getSort)
                .orderByDesc(DictPO::getCreateTime);
    }
}