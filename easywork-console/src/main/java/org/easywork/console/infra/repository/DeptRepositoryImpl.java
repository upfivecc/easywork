package org.easywork.console.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.easywork.console.domain.model.Dept;
import org.easywork.console.domain.model.dto.DeptQuery;
import org.easywork.console.domain.repository.DeptRepository;
import org.easywork.console.infra.common.utils.TreeUtils;
import org.easywork.console.infra.repository.base.BaseRepositoryImpl;
import org.easywork.console.infra.repository.converter.DeptConverter;
import org.easywork.console.infra.repository.mapper.DeptMapper;
import org.easywork.console.infra.repository.po.DeptPO;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
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
public class DeptRepositoryImpl extends BaseRepositoryImpl<DeptMapper, DeptPO, Dept, DeptQuery> implements DeptRepository {

    private final DeptMapper deptMapper;

    /**
     * 菜单持久化方法 - 包含特殊的层级和路径处理逻辑
     * 重写基类方法以处理菜单特有的业务逻辑
     */
    @Override
    public Dept persist(Dept menu) {
        DeptPO deptPO = DeptConverter.INSTANCE.toRepository(menu);

        if (deptPO.getId() == null) {
            // 新增操作
            deptPO.setCreateTime(LocalDateTime.now());
            deptPO.setDeleted(0);
            deptPO.setVersion(0);

            // 设置层级和路径（基于 parentCode）
            if (StringUtils.hasText(deptPO.getParentCode())) {
                Optional<Dept> parent = findByCode(deptPO.getParentCode());
                if (parent.isPresent()) {
                    Dept deptBO = parent.get();
                    deptPO.setLevel(deptBO.getLevel() + 1);
                    deptPO.setPath(deptPO.getPath() + "/" + deptPO.getCode());
                } else {
                    deptPO.setLevel(1);
                    deptPO.setPath(deptPO.getCode());
                }
            } else {
                deptPO.setLevel(1);
                deptPO.setParentCode(null);
                deptPO.setPath(deptPO.getCode());
            }

            super.save(deptPO);
        } else {
            // 更新操作
            deptPO.setUpdateTime(LocalDateTime.now());
            super.updateById(deptPO);
        }
        return DeptConverter.INSTANCE.toDomain(deptPO);
    }

    @Override
    public Optional<Dept> findByCode(String code) {
        LambdaQueryWrapper<DeptPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(DeptPO::getCode, code)
                .last("LIMIT 1");
        DeptPO deptPO = deptMapper.selectOne(queryWrapper);
        return Optional.ofNullable(deptPO)
                .map(DeptConverter.INSTANCE::toDomain);
    }

    @Override
    public List<Dept> findAllAsTree() {
        LambdaQueryWrapper<DeptPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(DeptPO::getStatus, 1)
                .orderByAsc(DeptPO::getLevel, DeptPO::getSort);
        List<DeptPO> deptPOs = deptMapper.selectList(queryWrapper);
        List<Dept> depts = deptPOs.stream()
                .map(DeptConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());

        return TreeUtils.buildTree(depts, (String) null);
    }

    @Override
    public List<Dept> findByParentCode(String parentCode) {
        LambdaQueryWrapper<DeptPO> queryWrapper = super.queryWrapper();
        if (parentCode == null || parentCode.isEmpty() || "0".equals(parentCode)) {
            queryWrapper.and(wrapper -> wrapper
                    .isNull(DeptPO::getParentCode)
                    .or().eq(DeptPO::getParentCode, "")
                    .or().eq(DeptPO::getParentCode, "0")
            );
        } else {
            queryWrapper.eq(DeptPO::getParentCode, parentCode);
        }
        queryWrapper.orderByAsc(DeptPO::getSort);
        List<DeptPO> deptPOs = deptMapper.selectList(queryWrapper);
        return deptPOs.stream()
                .map(DeptConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dept> findByType(Integer type) {
        LambdaQueryWrapper<DeptPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(DeptPO::getType, type)
                .orderByAsc(DeptPO::getLevel, DeptPO::getSort);
        List<DeptPO> deptPOs = deptMapper.selectList(queryWrapper);
        return deptPOs.stream()
                .map(DeptConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dept> findAllEnabled() {
        LambdaQueryWrapper<DeptPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(DeptPO::getStatus, 1)
                .orderByAsc(DeptPO::getLevel, DeptPO::getSort);
        List<DeptPO> deptPOs = deptMapper.selectList(queryWrapper);
        return deptPOs.stream()
                .map(DeptConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dept> findByLeaderId(Long leaderId) {
        LambdaQueryWrapper<DeptPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(DeptPO::getLeaderId, leaderId);
        List<DeptPO> deptPOs = deptMapper.selectList(queryWrapper);
        return deptPOs.stream()
                .map(DeptConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCode(String code) {
        LambdaQueryWrapper<DeptPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(DeptPO::getCode, code);
        return deptMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    protected void buildQuery(LambdaQueryWrapper<DeptPO> queryWrapper, DeptQuery pageQuery) {
        // 关键字搜索：部门名称、部门编码、负责人姓名
        String keyword = pageQuery.getKeyword();
        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                    .like(DeptPO::getName, keyword)
                    .or().like(DeptPO::getCode, keyword)
                    .or().like(DeptPO::getLeaderName, keyword)
            );
        }

        // 可以按部门状态过滤（可选）
        Integer status = pageQuery.getStatus();
        if (status != null) {
            queryWrapper.eq(DeptPO::getStatus, status);
        }

        // 可以按部门类型过滤（可选）
        Integer type = pageQuery.getType();
        if (type != null) {
            queryWrapper.eq(DeptPO::getType, type);
        }

        // 按层级和排序号排序，再按创建时间降序
        queryWrapper.orderByAsc(DeptPO::getLevel, DeptPO::getSort)
                .orderByDesc(DeptPO::getCreateTime);
    }

    @Override
    public List<String> findAllChildrenCodes(String deptCode) {
        // 基于 code 的新方法，使用专门的 Mapper 方法
        List<DeptPO> children = deptMapper.selectAllChildrenByCode(deptCode);
        return children.stream()
                .map(DeptPO::getCode)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dept> findByDataScope(Long userId, Integer dataScope) {
        // 根据数据权限范围查询部门
        // 这里需要根据具体的业务逻辑实现
        // 暂时返回所有启用的部门
        return findAllEnabled();
    }

}