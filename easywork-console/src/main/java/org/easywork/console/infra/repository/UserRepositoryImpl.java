package org.easywork.console.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.easywork.common.rest.result.PageInfo;
import org.easywork.console.domain.model.User;
import org.easywork.console.domain.model.dto.UserQuery;
import org.easywork.console.domain.repository.UserRepository;
import org.easywork.console.infra.repository.base.BaseRepositoryImpl;
import org.easywork.console.infra.repository.converter.UserConverter;
import org.easywork.console.infra.repository.mapper.UserMapper;
import org.easywork.console.infra.repository.po.UserPO;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
public class UserRepositoryImpl extends BaseRepositoryImpl<UserMapper, UserPO, User, UserQuery> implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public User persist(User user) {
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
        LambdaQueryWrapper<UserPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(UserPO::getUsername, username);
        UserPO userPO = userMapper.selectOne(queryWrapper);
        return Optional.ofNullable(userPO)
                .map(UserConverter.INSTANCE::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        LambdaQueryWrapper<UserPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(UserPO::getEmail, email);
        UserPO userPO = userMapper.selectOne(queryWrapper);
        return Optional.ofNullable(userPO)
                .map(UserConverter.INSTANCE::toDomain);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        LambdaQueryWrapper<UserPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(UserPO::getPhone, phone);
        UserPO userPO = userMapper.selectOne(queryWrapper);
        return Optional.ofNullable(userPO)
                .map(UserConverter.INSTANCE::toDomain);
    }

    @Override
    public List<User> findByPage(int page, int size, String keyword) {
        LambdaQueryWrapper<UserPO> queryWrapper = super.queryWrapper();

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
        LambdaQueryWrapper<UserPO> queryWrapper = super.queryWrapper();

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
        LambdaQueryWrapper<UserPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(UserPO::getDeptId, deptId)
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
        LambdaQueryWrapper<UserPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(UserPO::getUsername, username);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        LambdaQueryWrapper<UserPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(UserPO::getEmail, email);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public boolean existsByPhone(String phone) {
        LambdaQueryWrapper<UserPO> queryWrapper = super.queryWrapper();
        queryWrapper.eq(UserPO::getPhone, phone);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public PageInfo<User> findByPage(UserQuery query) {
        LambdaQueryWrapper<UserPO> queryWrapper = super.queryWrapper();
        
        // 关键字搜索：用户名、昵称、真实姓名、邮箱、手机号
        String keyword = query.getKeyword();
        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                    .like(UserPO::getUsername, keyword)
                    .or().like(UserPO::getNickname, keyword)
                    .or().like(UserPO::getRealName, keyword)
                    .or().like(UserPO::getEmail, keyword)
                    .or().like(UserPO::getPhone, keyword)
            );
        }
        
        // 按用户状态过滤（可选）
        Integer status = query.getStatus();
        if (status != null) {
            queryWrapper.eq(UserPO::getStatus, status);
        }
        
        // 按部门ID过滤（可选）
        Long deptId = query.getDeptId();
        if (deptId != null) {
            queryWrapper.eq(UserPO::getDeptId, deptId);
        }
        
        // 按性别过滤（可选）
        Integer gender = query.getGender();
        if (gender != null) {
            queryWrapper.eq(UserPO::getGender, gender);
        }
        
        // 按创建时间降序排序
        queryWrapper.orderByDesc(UserPO::getCreateTime);
        
        // 分页查询
        IPage<UserPO> pageParam = new Page<>(query.getPageNum(), query.getPageSize());
        IPage<UserPO> result = super.page(pageParam, queryWrapper);
        
        // 转换为域对象
        List<User> users = result.getRecords().stream()
                .map(UserConverter.INSTANCE::toDomain)
                .collect(Collectors.toList());
        
        // 构建分页结果
        return PageInfo.<User>builder()
                .page(query.getPageNum())
                .pageSize(query.getPageSize())
                .total(result.getTotal())
                .records(users)
                .build();
    }
}