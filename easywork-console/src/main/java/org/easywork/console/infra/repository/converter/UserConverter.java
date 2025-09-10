package org.easywork.console.infra.repository.converter;

import org.easywork.console.domain.model.User;
import org.easywork.console.infra.repository.po.UserPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 部门转换器
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Mapper
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    /**
     * 领域对象转持久化对象
     */
    UserPO toRepository(User user);

    /**
     * 持久化对象转领域对象
     */
    User toDomain(UserPO userPO);
}