package org.easywork.console.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.easywork.console.domain.model.Menu;
import org.easywork.console.domain.model.dto.MenuQuery;
import org.easywork.console.domain.repository.MenuRepository;
import org.easywork.console.infra.common.utils.TreeUtils;
import org.easywork.console.infra.repository.base.BaseRepositoryImpl;
import org.easywork.console.infra.repository.converter.MenuConverter;
import org.easywork.console.infra.repository.mapper.MenuMapper;
import org.easywork.console.infra.repository.po.MenuPO;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 菜单仓储实现类
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Repository
public class MenuRepositoryImpl extends BaseRepositoryImpl<MenuMapper, MenuPO, Menu, MenuQuery> implements MenuRepository {

    /**
     * 菜单持久化方法 - 包含特殊的层级和路径处理逻辑
     * 重写基类方法以处理菜单特有的业务逻辑
     */
    @Override
    public Menu persist(Menu menu) {
        MenuPO menuPO = MenuConverter.INSTANCE.toRepository(menu);

        if (menuPO.getId() == null) {
            // 新增操作
            menuPO.setCreateTime(LocalDateTime.now());
            menuPO.setDeleted(0);
            menuPO.setVersion(0);

            // 设置层级和路径
            if (menuPO.getParentId() != null && menuPO.getParentId() != 0) {
                Optional<Menu> parent = findById(menuPO.getParentId());
                if (parent.isPresent()) {
                    Menu parentBO = parent.get();
                    menuPO.setLevel(parentBO.getLevel() + 1);
                    menuPO.setPath(parentBO.getPath() + "/" + menuPO.getId());
                } else {
                    menuPO.setLevel(1);
                    menuPO.setPath("/" + menuPO.getId());
                }
            } else {
                menuPO.setLevel(1);
                menuPO.setParentId(0L);
                menuPO.setPath("/" + menuPO.getId());
            }

            super.save(menuPO);

            // 保存后更新路径（因为需要实际的ID）
            if (menuPO.getParentId() != null && menuPO.getParentId() != 0) {
                Optional<Menu> parent = findById(menuPO.getParentId());
                if (parent.isPresent()) {
                    menuPO.setPath(parent.get().getPath() + "/" + menuPO.getId());
                    super.updateById(menuPO);
                }
            } else {
                menuPO.setPath("/" + menuPO.getId());
                super.updateById(menuPO);
            }
        } else {
            // 更新操作
            menuPO.setUpdateTime(LocalDateTime.now());
            super.updateById(menuPO);
        }
        return MenuConverter.INSTANCE.toDomain(menuPO);
    }

    /**
     * 使用基类的通用 findById 方法
     * 菜单查询没有特殊逻辑，可以直接使用基类实现
     */
    // findById 方法已由基类 BaseRepositoryImpl 提供
    @Override
    public Optional<Menu> findByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return Optional.empty();
        }

        LambdaQueryWrapper<MenuPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(MenuPO::getCode, code);

        MenuPO menuPO = super.getOne(queryWrapper);
        return Optional.ofNullable(MenuConverter.INSTANCE.toDomain(menuPO));
    }

    @Override
    public List<Menu> findAllAsTree() {
        LambdaQueryWrapper<MenuPO> queryWrapper = super.queryWrapper();
        queryWrapper.orderByAsc(MenuPO::getLevel)
                .orderByAsc(MenuPO::getSort)
                .orderByDesc(MenuPO::getCreateTime);

        List<MenuPO> allMenus = super.list(queryWrapper);
        return TreeUtils.buildTree(allMenus.stream().map(MenuConverter.INSTANCE::toDomain).toList(), 0L);
    }

    @Override
    public List<Menu> findByParentId(Long parentId) {
        LambdaQueryWrapper<MenuPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(MenuPO::getParentId, parentId != null ? parentId : 0)
                .orderByAsc(MenuPO::getSort)
                .orderByDesc(MenuPO::getCreateTime);

        return super.list(queryWrapper).stream().map(MenuConverter.INSTANCE::toDomain).toList();
    }

    @Override
    public List<Menu> findByUserId(Long userId) {
        if (userId == null) {
            return List.of();
        }

        return baseMapper.selectByUserId(userId).stream().map(MenuConverter.INSTANCE::toDomain).toList();
    }

    @Override
    public List<Menu> findByRoleId(Long roleId) {
        if (roleId == null) {
            return List.of();
        }

        return baseMapper.selectByRoleId(roleId).stream().map(MenuConverter.INSTANCE::toDomain).toList();
    }

    @Override
    public List<Menu> findByType(Integer type) {
        if (type == null) {
            return List.of();
        }

        LambdaQueryWrapper<MenuPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(MenuPO::getType, type)
                .orderByAsc(MenuPO::getLevel)
                .orderByAsc(MenuPO::getSort)
                .orderByDesc(MenuPO::getCreateTime);

        return super.list(queryWrapper).stream().map(MenuConverter.INSTANCE::toDomain).toList();
    }

    @Override
    public List<Menu> findAllVisible() {
        LambdaQueryWrapper<MenuPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(MenuPO::getVisible, 1)
                .orderByAsc(MenuPO::getLevel)
                .orderByAsc(MenuPO::getSort)
                .orderByDesc(MenuPO::getCreateTime);

        List<MenuPO> allMenus = super.list(queryWrapper);
        return TreeUtils.buildTree(allMenus.stream().map(MenuConverter.INSTANCE::toDomain).toList(), 0L);
    }

    @Override
    public List<Menu> findAllEnabled() {
        LambdaQueryWrapper<MenuPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(MenuPO::getStatus, 1)
                .orderByAsc(MenuPO::getLevel)
                .orderByAsc(MenuPO::getSort)
                .orderByDesc(MenuPO::getCreateTime);

        List<MenuPO> allMenus = super.list(queryWrapper);
        return TreeUtils.buildTree(allMenus.stream().map(MenuConverter.INSTANCE::toDomain).toList(), 0L);
    }

    @Override
    public boolean existsByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return false;
        }

        LambdaQueryWrapper<MenuPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(MenuPO::getCode, code);

        return super.count(queryWrapper) > 0;
    }

    @Override
    protected void buildQuery(LambdaQueryWrapper<MenuPO> queryWrapper, MenuQuery query) {
        // 关键字搜索：菜单名称、菜单代码、路由路径
        String keyword = query.getKeyword();
        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                    .like(MenuPO::getName, keyword)
                    .or().like(MenuPO::getCode, keyword)
                    .or().like(MenuPO::getRoutePath, keyword)
            );
        }

        // 按菜单类型过滤（可选）
        Integer type = query.getType();
        if (type != null) {
            queryWrapper.eq(MenuPO::getType, type);
        }

        // 按菜单状态过滤（可选）
        Integer status = query.getStatus();
        if (status != null) {
            queryWrapper.eq(MenuPO::getStatus, status);
        }

        // 按可见性过滤（可选）
        Integer visible = query.getVisible();
        if (visible != null) {
            queryWrapper.eq(MenuPO::getVisible, visible);
        }

        // 按层级和排序号排序，再按创建时间降序
        queryWrapper.orderByAsc(MenuPO::getLevel, MenuPO::getSort)
                .orderByDesc(MenuPO::getCreateTime);
    }

}