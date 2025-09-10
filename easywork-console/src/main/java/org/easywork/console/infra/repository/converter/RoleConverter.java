package org.easywork.console.infra.repository.converter;

import org.easywork.console.domain.model.Role;
import org.easywork.console.infra.repository.po.RolePO;
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
public interface RoleConverter {

    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    /**
     * 领域对象转持久化对象
     */
    RolePO toRepository(Role role);

    /**
     * 持久化对象转领域对象
     */
    Role toDomain(RolePO rolePO);
}