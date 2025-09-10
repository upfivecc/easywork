package org.easywork.console.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.easywork.console.domain.model.User;
import org.easywork.console.domain.repository.UserRepository;
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
public class UserRepositoryImpl extends ServiceImpl<UserMapper, UserPO> implements UserRepository {
    
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserPO userPO = UserConverter.INSTANCE.toRepository(user);
        
        if (userPO.getId() == null) {
            // 新增
            userMapper.insert(userPO);
        } else {
            // 更新
            userMapper.updateById(userPO);
        }
        
        return UserConverter.INSTANCE.toDomain(userPO);
    }
    
    @Override
    public Optional<User> findById(Long id) {
        UserPO userPO = userMapper.selectById(id);
        return Optional.ofNullable(userPO)
                .map(UserConverter.INSTANCE::toDomain);
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        UserPO userPO = userMapper.selectByUsername(username);
        return Optional.ofNullable(userPO)
                .map(UserConverter.INSTANCE::toDomain);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        UserPO userPO = userMapper.selectByEmail(email);
        return Optional.ofNullable(userPO)
                .map(UserConverter.INSTANCE::toDomain);
    }
    
    @Override
    public Optional<User> findByPhone(String phone) {
        UserPO userPO = userMapper.selectByPhone(phone);
        return Optional.ofNullable(userPO)
                .map(UserConverter.INSTANCE::toDomain);
    }
    
    @Override
    public List<User> findByPage(int page, int size, String keyword) {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
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
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
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
        List<UserPO> userPOs = userMapper.selectByDeptId(deptId);
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
        return userMapper.countByUsername(username) > 0;
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userMapper.countByEmail(email) > 0;
    }
    
    @Override
    public boolean existsByPhone(String phone) {
        return userMapper.countByPhone(phone) > 0;
    }
    
    @Override
    public void deleteById(Long id) {
        // 逻辑删除
        UserPO userPO = new UserPO();
        userPO.setId(id);
        userPO.setDeleted(1);
        userMapper.updateById(userPO);
    }
    
    @Override
    public void deleteByIds(List<Long> ids) {
        ids.forEach(this::deleteById);
    }
}