package org.easywork.console.infra.repository.converter;

import org.easywork.console.domain.model.Dept;
import org.easywork.console.infra.repository.po.DeptPO;
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
public interface DeptConverter extends BaseConverter<DeptPO, Dept> {

    DeptConverter INSTANCE = Mappers.getMapper(DeptConverter.class);

}