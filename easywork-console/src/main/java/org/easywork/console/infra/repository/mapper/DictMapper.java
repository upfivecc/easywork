package org.easywork.console.infra.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
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
    
    // 所有简单查询都移到 Repository 实现类中使用 LambdaQueryWrapper
    
}