package org.easywork.console.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.easywork.console.domain.model.Menu;
import org.easywork.console.domain.repository.MenuRepository;
import org.easywork.console.infra.repository.converter.MenuConverter;
import org.easywork.console.infra.repository.mapper.MenuMapper;
import org.easywork.console.infra.repository.po.MenuPO;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 菜单仓储实现类
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Repository
public class MenuRepositoryImpl extends ServiceImpl<MenuMapper, MenuPO> implements MenuRepository {

    @Override
    public Menu save(Menu menu) {
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

    @Override
    public Optional<Menu> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        LambdaQueryWrapper<MenuPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuPO::getId, id)
                .eq(MenuPO::getDeleted, 0);

        MenuPO menuPO = super.getOne(queryWrapper);
        return Optional.ofNullable(MenuConverter.INSTANCE.toDomain(menuPO));
    }

    @Override
    public Optional<Menu> findByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return Optional.empty();
        }

        LambdaQueryWrapper<MenuPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuPO::getCode, code)
                .eq(MenuPO::getDeleted, 0);

        MenuPO menuPO = super.getOne(queryWrapper);
        return Optional.ofNullable(MenuConverter.INSTANCE.toDomain(menuPO));
    }

    @Override
    public List<Menu> findAllAsTree() {
        LambdaQueryWrapper<MenuPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuPO::getDeleted, 0)
                .orderByAsc(MenuPO::getLevel)
                .orderByAsc(MenuPO::getSort)
                .orderByDesc(MenuPO::getCreateTime);

        List<MenuPO> allMenus = super.list(queryWrapper);
        return buildTree(allMenus.stream().map(MenuConverter.INSTANCE::toDomain).toList());
    }

    @Override
    public List<Menu> findByParentId(Long parentId) {
        LambdaQueryWrapper<MenuPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuPO::getParentId, parentId != null ? parentId : 0)
                .eq(MenuPO::getDeleted, 0)
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

        LambdaQueryWrapper<MenuPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuPO::getType, type)
                .eq(MenuPO::getDeleted, 0)
                .orderByAsc(MenuPO::getLevel)
                .orderByAsc(MenuPO::getSort)
                .orderByDesc(MenuPO::getCreateTime);

        return super.list(queryWrapper).stream().map(MenuConverter.INSTANCE::toDomain).toList();
    }

    @Override
    public List<Menu> findAllVisible() {
        LambdaQueryWrapper<MenuPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuPO::getVisible, 1)
                .eq(MenuPO::getDeleted, 0)
                .orderByAsc(MenuPO::getLevel)
                .orderByAsc(MenuPO::getSort)
                .orderByDesc(MenuPO::getCreateTime);

        List<MenuPO> allMenus = super.list(queryWrapper);
        return buildTree(allMenus.stream().map(MenuConverter.INSTANCE::toDomain).toList());
    }

    @Override
    public List<Menu> findAllEnabled() {
        LambdaQueryWrapper<MenuPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuPO::getStatus, 1)
                .eq(MenuPO::getDeleted, 0)
                .orderByAsc(MenuPO::getLevel)
                .orderByAsc(MenuPO::getSort)
                .orderByDesc(MenuPO::getCreateTime);

        List<MenuPO> allMenus = super.list(queryWrapper);
        return buildTree(allMenus.stream().map(MenuConverter.INSTANCE::toDomain).toList());
    }

    @Override
    public boolean existsByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return false;
        }

        LambdaQueryWrapper<MenuPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuPO::getCode, code)
                .eq(MenuPO::getDeleted, 0);

        return super.count(queryWrapper) > 0;
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            return;
        }

        // 逻辑删除
        MenuPO menuPO = new MenuPO();
        menuPO.setId(id);
        menuPO.setDeleted(1);
        menuPO.setUpdateTime(LocalDateTime.now());

        super.updateById(menuPO);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }

        // 批量逻辑删除
        LambdaQueryWrapper<MenuPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MenuPO::getId, ids);

        MenuPO updateEntity = new MenuPO();
        updateEntity.setDeleted(1);
        updateEntity.setUpdateTime(LocalDateTime.now());

        super.update(updateEntity, queryWrapper);
    }

    /**
     * 构建菜单树形结构
     */
    private List<Menu> buildTree(List<Menu> allMenus) {
        if (allMenus == null || allMenus.isEmpty()) {
            return new ArrayList<>();
        }

        // 按父ID分组
        Map<Long, List<Menu>> groupByParent = allMenus.stream()
                .collect(Collectors.groupingBy(menu ->
                        menu.getParentId() != null ? menu.getParentId() : 0L));

        // 构建树形结构
        List<Menu> rootMenus = groupByParent.getOrDefault(0L, new ArrayList<>());

        for (Menu menu : allMenus) {
            List<Menu> children = groupByParent.get(menu.getId());
            if (children != null && !children.isEmpty()) {
                menu.setChildren(children);
                menu.setIsLeaf(false);
            } else {
                menu.setIsLeaf(true);
            }
        }

        return rootMenus;
    }
}