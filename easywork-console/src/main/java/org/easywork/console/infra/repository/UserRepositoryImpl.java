package org.easywork.console.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.easywork.console.domain.model.User;
import org.easywork.console.domain.repository.UserRepository;
import org.easywork.console.infra.repository.converter.UserConverter;
import org.easywork.console.infra.repository.mapper.UserMapper;
import org.easywork.console.infra.repository.po.UserPO;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户仓储实现
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl extends BaseRepositoryImpl<UserMapper, UserPO> implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserPO userPO = UserConverter.INSTANCE.toRepository(user);
        UserPO savedPo = savePo(userPO);
        return UserConverter.INSTANCE.toDomain(savedPo);
    }

    @Override
    public Optional<User> findById(Long id) {
        UserPO userPO = userMapper.selectById(id);
        return Optional.ofNullable(userPO)
                .map(UserConverter.INSTANCE::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        LambdaQueryWrapper<UserPO> queryWrapper = Wrappers.lambdaQuery(UserPO.class);
        queryWrapper.eq(UserPO::getUsername, username)
                .eq(UserPO::getDeleted, 0);
        UserPO userPO = userMapper.selectOne(queryWrapper);
        return Optional.ofNullable(userPO)
                .map(UserConverter.INSTANCE::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        LambdaQueryWrapper<UserPO> queryWrapper = Wrappers.lambdaQuery(UserPO.class);
        queryWrapper.eq(UserPO::getEmail, email)
                .eq(UserPO::getDeleted, 0);
        UserPO userPO = userMapper.selectOne(queryWrapper);
        return Optional.ofNullable(userPO)
                .map(UserConverter.INSTANCE::toDomain);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        LambdaQueryWrapper<UserPO> queryWrapper = Wrappers.lambdaQuery(UserPO.class);
        queryWrapper.eq(UserPO::getPhone, phone)
                .eq(UserPO::getDeleted, 0);
        UserPO userPO = userMapper.selectOne(queryWrapper);
        return Optional.ofNullable(userPO)
                .map(UserConverter.INSTANCE::toDomain);
    }

    @Override
    public List<User> findByPage(int page, int size, String keyword) {
        LambdaQueryWrapper<UserPO> queryWrapper = Wrappers.lambdaQuery(UserPO.class);
        queryWrapper.eq(UserPO::getDeleted, 0);

        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                    .like(UserPO::getUsername, keyword)
                    .or().like(UserPO::getNickname, keyword)
                    .or().like(UserPO::getRealName, keyword)
                    .or().like(UserPO::getEmail, keyword)
                    .or().like(UserPO::getPhone, keyword)
            );
        }

        queryWrapper.orderByDesc(UserPO::getCreateTime);

        Page<UserPO> pageParam = new Page<>(page, size);
        Page<UserPO> pageResult = userMapper.selectPage(pageParam, queryWrapper);

        return pageResult.getRecords().stream()
                .map(UserConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long count(String keyword) {
        LambdaQueryWrapper<UserPO> queryWrapper = Wrappers.lambdaQuery(UserPO.class);
        queryWrapper.eq(UserPO::getDeleted, 0);

        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                    .like(UserPO::getUsername, keyword)
                    .or().like(UserPO::getNickname, keyword)
                    .or().like(UserPO::getRealName, keyword)
                    .or().like(UserPO::getEmail, keyword)
                    .or().like(UserPO::getPhone, keyword)
            );
        }

        return userMapper.selectCount(queryWrapper);
    }

    @Override
    public List<User> findByDeptId(Long deptId) {
        LambdaQueryWrapper<UserPO> queryWrapper = Wrappers.lambdaQuery(UserPO.class);
        queryWrapper.eq(UserPO::getDeptId, deptId)
                .eq(UserPO::getDeleted, 0)
                .orderByDesc(UserPO::getCreateTime);
        List<UserPO> userPOs = userMapper.selectList(queryWrapper);
        return userPOs.stream()
                .map(UserConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByRoleId(Long roleId) {
        List<UserPO> userPOs = userMapper.selectByRoleId(roleId);
        return userPOs.stream()
                .map(UserConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByUsername(String username) {
        LambdaQueryWrapper<UserPO> queryWrapper = Wrappers.lambdaQuery(UserPO.class);
        queryWrapper.eq(UserPO::getUsername, username)
                .eq(UserPO::getDeleted, 0);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        LambdaQueryWrapper<UserPO> queryWrapper = Wrappers.lambdaQuery(UserPO.class);
        queryWrapper.eq(UserPO::getEmail, email)
                .eq(UserPO::getDeleted, 0);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public boolean existsByPhone(String phone) {
        LambdaQueryWrapper<UserPO> queryWrapper = Wrappers.lambdaQuery(UserPO.class);
        queryWrapper.eq(UserPO::getPhone, phone)
                .eq(UserPO::getDeleted, 0);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public void deleteById(Long id) {
        // 逻辑删除
        this.logicalDeleteById(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        // 批量逻辑删除
        LambdaQueryWrapper<UserPO> queryWrapper = Wrappers.lambdaQuery(UserPO.class);
        queryWrapper.in(UserPO::getId, ids);

        UserPO updateEntity = new UserPO();
        updateEntity.setDeleted(1);
        updateEntity.setUpdateTime(LocalDateTime.now());

        super.update(updateEntity, queryWrapper);
    }
}