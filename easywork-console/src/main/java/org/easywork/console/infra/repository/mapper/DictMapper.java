package org.easywork.console.infra.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.easywork.console.infra.repository.po.DictPO;

/**
 * 字典Mapper接口
 * 
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Mapper
public interface DictMapper extends BaseMapper<DictPO> {
    
    /**
     * 根据字典编码查询字典
     */
    @Select("SELECT * FROM sys_dict WHERE code = #{code} AND deleted = 0 LIMIT 1")
    DictPO selectByCode(@Param("code") String code);
}