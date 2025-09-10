package org.easywork.console.infra.repository.converter;

import org.easywork.console.domain.model.Permission;
import org.easywork.console.infra.repository.po.PermissionPO;
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
public interface PermissionConverter extends BaseConverter<PermissionPO, Permission>{

    PermissionConverter INSTANCE = Mappers.getMapper(PermissionConverter.class);

}