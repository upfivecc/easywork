package org.easywork.console.infra.repository.converter;

import org.easywork.console.domain.model.Dict;
import org.easywork.console.infra.repository.po.DictPO;
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
public interface DictConverter extends BaseConverter<DictPO, Dict>{

    DictConverter INSTANCE = Mappers.getMapper(DictConverter.class);


}