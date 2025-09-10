package org.easywork.console.infra.repository.converter;

import org.easywork.console.domain.model.Menu;
import org.easywork.console.infra.repository.po.MenuPO;
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
public interface MenuConverter {

    MenuConverter INSTANCE = Mappers.getMapper(MenuConverter.class);

    /**
     * 领域对象转持久化对象
     */
    MenuPO toRepository(Menu menu);

    /**
     * 持久化对象转领域对象
     */
    Menu toDomain(MenuPO menuPO);
}