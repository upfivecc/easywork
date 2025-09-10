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
public interface DeptConverter {
    
    DeptConverter INSTANCE = Mappers.getMapper(DeptConverter.class);
    
    /**
     * 领域对象转持久化对象
     */
    DeptPO toRepository(Dept dept);
    
    /**
     * 持久化对象转领域对象
     */
    Dept toDomain(DeptPO deptPO);
}