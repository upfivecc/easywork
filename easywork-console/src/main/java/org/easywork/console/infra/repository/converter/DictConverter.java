package org.easywork.console.infra.repository.converter;

import cn.hutool.core.lang.Dict;
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
public interface DictConverter {

    DictConverter INSTANCE = Mappers.getMapper(DictConverter.class);

    /**
     * 领域对象转持久化对象
     */
    DictPO toRepository(Dict dict);

    /**
     * 持久化对象转领域对象
     */
    Dict toDomain(DictPO dictPO);
}